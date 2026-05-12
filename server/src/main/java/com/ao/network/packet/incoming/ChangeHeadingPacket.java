package com.ao.network.packet.incoming;

import com.ao.model.map.Heading;
import com.ao.model.user.LoggedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import org.tinylog.Logger;

public class ChangeHeadingPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        // El cliente envia direcciones en base 1 (herencia VB6: 1=Norte, 2=Este, 3=Sur, 4=Oeste)
        byte rawHeading = buffer.get();
        // Resta 1 para convertir al indice base 0 del enum Heading del servidor
        Heading heading = Heading.get((byte) (rawHeading - 1));

        if (heading == null) {
            Logger.warn("Valor de heading invalido recibido: {}", rawHeading & 0xFF);
            return true;
        }

        ((LoggedUser) connection.getUser()).setHeading(heading);
        return true;
    }

}
