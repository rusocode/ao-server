package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import org.tinylog.Logger;

public class LeftClickPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        // Necesitamos leer 2 bytes (X e Y) para limpiar el buffer, aunque no los usemos todavia.
        if (buffer.getReadableBytes() < 2) {
            return false;
        }

        byte x = buffer.get();
        byte y = buffer.get();

        Logger.debug("Left click received at X: {}, Y: {} - Ignoring for now.", x, y);

        return true;
    }
}
