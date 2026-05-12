package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import org.tinylog.Logger;

import com.ao.context.ApplicationContext;
import com.ao.model.character.Alignment;
import com.ao.model.character.Character;
import com.ao.model.fonts.Font;
import com.ao.model.user.LoggedUser;
import com.ao.model.user.User;
import com.ao.model.map.Map;
import com.ao.network.packet.outgoing.ConsoleMessagePacket;
import com.ao.model.character.UserCharacter;
import com.ao.service.MapService;

public class LeftClickPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        // Necesitamos leer 2 bytes (X e Y) para limpiar el buffer, aunque no los usemos
        // todavia.
        if (buffer.getReadableBytes() < 2) {
            return false;
        }

        byte x = buffer.get();
        byte y = buffer.get();

        User userIndex = connection.getUser();

        if (userIndex instanceof LoggedUser user) {

            MapService mapService = ApplicationContext.getInstance(MapService.class);
            Map map = mapService.getMap(user.getPosition().getMap());
            Character target = map.getTile(x, y).getCharacter();

            if (target != null) {
                Alignment alignment = target.getReputation().getAlignment();
                String alignMsg = (alignment == Alignment.CITIZEN) ? "Ciudadano" : "Criminal";
                Font font = (alignment == Alignment.CITIZEN) ? Font.CITIZEN : Font.FIGHT;

                String raza = "N/A";
                String clase = "N/A";

                if (target instanceof UserCharacter userTarget) {
                    raza = userTarget.getRace().name();
                    clase = userTarget.getArchetype().getClass().getSimpleName().replace("Archetype", "");
                }

                String message = "Ves a " + target.getName() + " (" + raza + " " + clase + ", Nivel "
                        + target.getLevel() + ") [" + alignMsg + "]";

                connection.send(new ConsoleMessagePacket(message, font));
            }
        }

        return true;
    }
}
