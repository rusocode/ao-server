package com.ao.network.packet.incoming;

import com.ao.config.IntervalsConfig;
import com.ao.context.ApplicationContext;
import com.ao.model.character.Privileges;
import com.ao.model.character.UserCharacter;
import com.ao.model.map.Map;
import com.ao.model.map.Position;
import com.ao.model.map.Tile;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.LoggedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.outgoing.ConsoleMessagePacket;
import com.ao.network.packet.outgoing.DisconnectPacket;
import com.ao.service.MapService;
import com.ao.service.TimedEventsService;
import com.ao.service.timedevents.TimedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class QuitPacketTest {

    private QuitPacket packet;
    private DataBuffer buffer;
    private Connection connection;
    private LoggedUser character;
    private MapService mapService;
    private TimedEventsService timedEventsService;
    private IntervalsConfig intervals;
    private IntervalsConfig.NetworkConfig networkConfig;
    private Map map;
    private Tile tile;

    @BeforeEach
    void setUp() {
        packet = new QuitPacket();
        buffer = mock(DataBuffer.class);
        connection = mock(Connection.class);
        character = mock(LoggedUser.class);
        mapService = mock(MapService.class);
        timedEventsService = mock(TimedEventsService.class);
        intervals = mock(IntervalsConfig.class);
        networkConfig = mock(IntervalsConfig.NetworkConfig.class);
        map = mock(Map.class);
        tile = mock(Tile.class);

        when(connection.getUser()).thenReturn(character);
        when(character.getPrivileges()).thenReturn(new Privileges(0x01)); // user, not GM
        when(character.isParalyzed()).thenReturn(false);
        when(character.isImmobilized()).thenReturn(false);

        Position pos = mock(Position.class);
        when(pos.getMap()).thenReturn(1);
        when(pos.getX()).thenReturn((byte) 10);
        when(pos.getY()).thenReturn((byte) 10);
        when(character.getPosition()).thenReturn(pos);

        when(mapService.getMap(1)).thenReturn(map);
        when(map.getTile(10, 10)).thenReturn(tile);
        when(tile.isSafeZone()).thenReturn(false);
        when(map.isPk()).thenReturn(true);

        when(intervals.getNetwork()).thenReturn(networkConfig);
        when(networkConfig.getCloseConnection()).thenReturn(10);

        when(connection.isConnected()).thenReturn(true);
    }

    @Test
    void handle_userIsNotUserCharacter_disconnects() {
        ConnectedUser notACharacter = mock(ConnectedUser.class);
        when(connection.getUser()).thenReturn(notACharacter);

        boolean result = packet.handle(buffer, connection);

        verify(connection).disconnect();
        assertThat(result).isFalse();
    }

    @Test
    void handle_gameMaster_disconnectsImmediately() {
        when(character.getPrivileges()).thenReturn(new Privileges(0x08)); // DIOS flag

        boolean result = packet.handle(buffer, connection);

        verify(connection).send(any(DisconnectPacket.class));
        verify(connection).disconnect();
        assertThat(result).isTrue();
    }

    @Test
    void handle_paralyzedCharacter_sendsErrorAndDoesNotDisconnect() {
        when(character.isParalyzed()).thenReturn(true);

        boolean result = packet.handle(buffer, connection);

        ArgumentCaptor<ConsoleMessagePacket> captor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
        verify(connection).send(captor.capture());
        assertThat(captor.getValue().message()).contains("paralizado");
        verify(connection, never()).disconnect();
        assertThat(result).isTrue();
    }

    @Test
    void handle_immobilizedCharacter_sendsErrorAndDoesNotDisconnect() {
        when(character.isImmobilized()).thenReturn(true);

        boolean result = packet.handle(buffer, connection);

        ArgumentCaptor<ConsoleMessagePacket> captor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
        verify(connection).send(captor.capture());
        assertThat(captor.getValue().message()).contains("inmovilizado");
        verify(connection, never()).disconnect();
        assertThat(result).isTrue();
    }

    @Test
    void handle_nullMap_disconnectsImmediately() {
        when(mapService.getMap(1)).thenReturn(null);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            boolean result = packet.handle(buffer, connection);

            verify(connection).send(any(DisconnectPacket.class));
            verify(connection).disconnect();
            assertThat(result).isTrue();
        }
    }

    @Test
    void handle_safeZone_disconnectsImmediately() {
        when(tile.isSafeZone()).thenReturn(true);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            boolean result = packet.handle(buffer, connection);

            verify(connection).send(any(DisconnectPacket.class));
            verify(connection).disconnect();
            assertThat(result).isTrue();
        }
    }

    @Test
    void handle_nonPkMap_disconnectsImmediately() {
        when(map.isPk()).thenReturn(false);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            boolean result = packet.handle(buffer, connection);

            verify(connection).send(any(DisconnectPacket.class));
            verify(connection).disconnect();
            assertThat(result).isTrue();
        }
    }

    @Test
    void handle_pkMapUnsafeTile_startsCountdown() {
        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);
            ctx.when(() -> ApplicationContext.getInstance(IntervalsConfig.class)).thenReturn(intervals);
            ctx.when(() -> ApplicationContext.getInstance(TimedEventsService.class)).thenReturn(timedEventsService);

            boolean result = packet.handle(buffer, connection);

            verify(timedEventsService).addEvent(eq((UserCharacter) character), any(TimedEvent.class), eq(1000L), eq(1000L), eq(10_000L));
            verify(connection, never()).disconnect();
            assertThat(result).isTrue();
        }
    }

    @Test
    void logoutEvent_execute_connectionDisconnected_doesNothing() {
        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);
            ctx.when(() -> ApplicationContext.getInstance(IntervalsConfig.class)).thenReturn(intervals);
            ctx.when(() -> ApplicationContext.getInstance(TimedEventsService.class)).thenReturn(timedEventsService);

            packet.handle(buffer, connection);

            ArgumentCaptor<TimedEvent> eventCaptor = ArgumentCaptor.forClass(TimedEvent.class);
            verify(timedEventsService).addEvent(eq((UserCharacter) character), eventCaptor.capture(), anyLong(), anyLong(), anyLong());

            when(connection.isConnected()).thenReturn(false);
            eventCaptor.getValue().execute();

            verify(connection, never()).disconnect();
        }
    }

    @Test
    void logoutEvent_execute_lastTick_disconnects() {
        when(networkConfig.getCloseConnection()).thenReturn(1);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);
            ctx.when(() -> ApplicationContext.getInstance(IntervalsConfig.class)).thenReturn(intervals);
            ctx.when(() -> ApplicationContext.getInstance(TimedEventsService.class)).thenReturn(timedEventsService);

            packet.handle(buffer, connection);

            ArgumentCaptor<TimedEvent> eventCaptor = ArgumentCaptor.forClass(TimedEvent.class);
            verify(timedEventsService).addEvent(eq((UserCharacter) character), eventCaptor.capture(), anyLong(), anyLong(), anyLong());

            eventCaptor.getValue().execute(); // remainingSeconds: 1 → 0

            verify(connection).send(any(DisconnectPacket.class));
            verify(connection).disconnect();
        }
    }

    @Test
    void logoutEvent_execute_intermediateTick_sendsCountdownMessage() {
        when(networkConfig.getCloseConnection()).thenReturn(3);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);
            ctx.when(() -> ApplicationContext.getInstance(IntervalsConfig.class)).thenReturn(intervals);
            ctx.when(() -> ApplicationContext.getInstance(TimedEventsService.class)).thenReturn(timedEventsService);

            packet.handle(buffer, connection);

            ArgumentCaptor<TimedEvent> eventCaptor = ArgumentCaptor.forClass(TimedEvent.class);
            verify(timedEventsService).addEvent(eq((UserCharacter) character), eventCaptor.capture(), anyLong(), anyLong(), anyLong());

            reset(connection);
            when(connection.isConnected()).thenReturn(true);
            eventCaptor.getValue().execute(); // remainingSeconds: 3 → 2

            ArgumentCaptor<ConsoleMessagePacket> msgCaptor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
            verify(connection).send(msgCaptor.capture());
            assertThat(msgCaptor.getValue().message()).contains("2");
            verify(connection, never()).disconnect();
        }
    }
}
