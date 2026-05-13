package com.ao.network.packet.incoming;

import com.ao.context.ApplicationContext;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.Character;
import com.ao.model.fonts.Font;
import com.ao.model.map.Map;
import com.ao.model.map.Position;
import com.ao.model.map.Tile;
import com.ao.model.user.User;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ConsoleMessagePacket;
import com.ao.network.packet.outgoing.DisconnectPacket;
import com.ao.service.MapService;
import com.ao.config.IntervalsConfig;
import com.ao.service.TimedEventsService;
import com.ao.service.timedevents.TimedEvent;

/**
 * Maneja el paquete de salida (QUIT).
 */
public class QuitPacket implements IncomingPacket {

    private final MapService mapService;
    private final IntervalsConfig intervals;
    private final TimedEventsService timedEventsService;

    public QuitPacket() {
        this(ApplicationContext.getInstance(MapService.class),
                ApplicationContext.getInstance(IntervalsConfig.class),
                ApplicationContext.getInstance(TimedEventsService.class));
    }

    QuitPacket(MapService mapService, IntervalsConfig intervals, TimedEventsService timedEventsService) {
        this.mapService = mapService;
        this.intervals = intervals;
        this.timedEventsService = timedEventsService;
    }

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        User userIndex = connection.getUser();

        if (!(userIndex instanceof UserCharacter)) {
            connection.disconnect();
            return false;
        }

        UserCharacter character = (UserCharacter) userIndex;

        // 1. Los GMs salen instantáneamente sin importar su estado
        if (character.getPrivileges().isGameMaster()) {
            connection.send(new ConsoleMessagePacket("¡Gracias por jugar a Argentum Online Java!", Font.INFO));
            connection.send(new DisconnectPacket());
            connection.disconnect();
            return true;
        }

        // 2. Jugadores normales no pueden salir si están paralizados o inmovilizados
        if (character.isParalyzed() || character.isImmobilized()) {
            connection.send(
                    new ConsoleMessagePacket("¡No puedes salir mientras estás paralizado o inmovilizado!", Font.INFO));
            return true;
        }

        // 3. Obtener información del mapa y posición
        Position pos = character.getPosition();
        Map map = mapService.getMap(pos.getMap());
        if (map == null) {
            connection.send(new ConsoleMessagePacket("Gracias por jugar a Argentum Online Java!", Font.INFO));
            connection.send(new DisconnectPacket());
            connection.disconnect();
            return true;
        }
        Tile tile = map.getTile(pos.getX(), pos.getY());

        if (tile.isSafeZone() || !map.isPk()) {
            // Salida Instantánea en zona segura
            connection.send(new ConsoleMessagePacket("Gracias por jugar a Argentum Online Java!", Font.INFO));
            connection.send(new DisconnectPacket());
            connection.disconnect();
        } else {
            // Salida con cuenta regresiva en zona insegura
            int seconds = intervals.getNetwork().getCloseConnection();

            connection.send(new ConsoleMessagePacket("Saliendo en " + seconds + " segundos...", Font.INFO));

            // Registramos el evento: inicia en 1s, se repite cada 1s, durante el tiempo
            // total configurado
            LogoutEvent logoutEvent = new LogoutEvent(character, connection, seconds);
            timedEventsService.addEvent(character, logoutEvent, 1000, 1000, seconds * 1000L);
        }

        return true;
    }

    /**
     * Evento temporizado para la cuenta regresiva y desconexión.
     */
    private static class LogoutEvent extends TimedEvent {

        private final Connection connection;
        private int remainingSeconds;

        public LogoutEvent(Character chara, Connection connection, int seconds) {
            super(chara);
            this.connection = connection;
            this.remainingSeconds = seconds;
        }

        @Override
        public void execute() {
            if (!connection.isConnected()) return;
            remainingSeconds--;
            if (remainingSeconds <= 0) {
                connection.send(new ConsoleMessagePacket("Gracias por jugar a Argentum Online Java!", Font.INFO));
                connection.send(new DisconnectPacket());
                connection.disconnect();
            } else connection.send(new ConsoleMessagePacket("Saliendo en " + remainingSeconds + " segundos...", Font.INFO));
        }

    }

}
