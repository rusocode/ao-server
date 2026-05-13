package com.ao.network.packet.incoming;

import com.ao.context.ApplicationContext;
import com.ao.model.character.Alignment;
import com.ao.model.character.Reputation;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.Archetype;
import com.ao.model.character.archetype.MageArchetype;
import com.ao.model.fonts.Font;
import com.ao.model.map.Map;
import com.ao.model.map.Position;
import com.ao.model.map.Tile;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.LoggedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.outgoing.ConsoleMessagePacket;
import com.ao.service.MapService;
import com.ao.model.character.Character;
import com.ao.model.character.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LeftClickPacketTest {

    private LeftClickPacket packet;
    private DataBuffer buffer;
    private Connection connection;
    private LoggedUser user;
    private MapService mapService;
    private Map map;
    private Tile tile;

    @BeforeEach
    void setUp() {
        packet = new LeftClickPacket();
        buffer = mock(DataBuffer.class);
        connection = mock(Connection.class);
        user = mock(LoggedUser.class);
        mapService = mock(MapService.class);
        map = mock(Map.class);
        tile = mock(Tile.class);

        when(buffer.getReadableBytes()).thenReturn(2);
        when(buffer.get()).thenReturn((byte) 5).thenReturn((byte) 10);
        when(connection.getUser()).thenReturn(user);

        Position pos = mock(Position.class);
        when(pos.getMap()).thenReturn(1);
        when(user.getPosition()).thenReturn(pos);

        when(mapService.getMap(1)).thenReturn(map);
        when(map.getTile(5, 10)).thenReturn(tile);
        when(tile.getCharacter()).thenReturn(null);
    }

    @Test
    void handle_sufficientBytes_readsTwoBytesAndReturnsTrue() {
        when(connection.getUser()).thenReturn(mock(ConnectedUser.class));

        boolean result = packet.handle(buffer, connection);

        assertThat(result).isTrue();
        verify(buffer, times(2)).get();
    }

    @Test
    void handle_insufficientBytes_returnsFalseWithoutReading() {
        when(buffer.getReadableBytes()).thenReturn(1);

        boolean result = packet.handle(buffer, connection);

        assertThat(result).isFalse();
        verify(buffer, never()).get();
    }

    @Test
    void handle_userNotLoggedUser_returnsTrueWithoutSendingMessage() {
        when(connection.getUser()).thenReturn(mock(ConnectedUser.class));

        boolean result = packet.handle(buffer, connection);

        assertThat(result).isTrue();
        verify(connection, never()).send(any());
    }

    @Test
    void handle_mapIsNull_returnsTrueWithoutSendingMessage() {
        when(mapService.getMap(1)).thenReturn(null);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            boolean result = packet.handle(buffer, connection);

            assertThat(result).isTrue();
            verify(connection, never()).send(any());
        }
    }

    @Test
    void handle_noCharacterAtTile_returnsTrueWithoutSendingMessage() {
        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            boolean result = packet.handle(buffer, connection);

            assertThat(result).isTrue();
            verify(connection, never()).send(any());
        }
    }

    @Test
    void handle_citizenCharacterAtTile_sendsMessageWithCitizenFont() {
        Character target = mock(Character.class);
        Reputation rep = mock(Reputation.class);
        when(tile.getCharacter()).thenReturn(target);
        when(target.getReputation()).thenReturn(rep);
        when(rep.getAlignment()).thenReturn(Alignment.CITIZEN);
        when(target.getName()).thenReturn("Hero");
        when(target.getLevel()).thenReturn((byte) 5);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            packet.handle(buffer, connection);

            ArgumentCaptor<ConsoleMessagePacket> captor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
            verify(connection).send(captor.capture());
            assertThat(captor.getValue().message()).contains("Hero").contains("Ciudadano");
            assertThat(captor.getValue().font()).isEqualTo(Font.CITIZEN);
        }
    }

    @Test
    void handle_criminalCharacterAtTile_sendsMessageWithFightFont() {
        Character target = mock(Character.class);
        Reputation rep = mock(Reputation.class);
        when(tile.getCharacter()).thenReturn(target);
        when(target.getReputation()).thenReturn(rep);
        when(rep.getAlignment()).thenReturn(Alignment.CRIMINAL);
        when(target.getName()).thenReturn("Villain");
        when(target.getLevel()).thenReturn((byte) 10);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            packet.handle(buffer, connection);

            ArgumentCaptor<ConsoleMessagePacket> captor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
            verify(connection).send(captor.capture());
            assertThat(captor.getValue().message()).contains("Villain").contains("Criminal");
            assertThat(captor.getValue().font()).isEqualTo(Font.FIGHT);
        }
    }

    @Test
    void handle_userCharacterAtTile_includesRaceAndArchetypeName() {
        UserCharacter target = mock(UserCharacter.class);
        Reputation rep = mock(Reputation.class);
        Archetype archetype = mock(MageArchetype.class);

        when(tile.getCharacter()).thenReturn(target);
        when(target.getReputation()).thenReturn(rep);
        when(rep.getAlignment()).thenReturn(Alignment.CITIZEN);
        when(target.getName()).thenReturn("Mago");
        when(target.getLevel()).thenReturn((byte) 20);
        when(target.getRace()).thenReturn(Race.ELF);
        when(target.getArchetype()).thenReturn(archetype);

        try (MockedStatic<ApplicationContext> ctx = mockStatic(ApplicationContext.class)) {
            ctx.when(() -> ApplicationContext.getInstance(MapService.class)).thenReturn(mapService);

            packet.handle(buffer, connection);

            ArgumentCaptor<ConsoleMessagePacket> captor = ArgumentCaptor.forClass(ConsoleMessagePacket.class);
            verify(connection).send(captor.capture());
            String message = captor.getValue().message();
            assertThat(message).contains("Mago").contains("ELF").contains("Mage");
        }
    }
}
