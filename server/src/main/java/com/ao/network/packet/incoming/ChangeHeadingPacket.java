package com.ao.network.packet.incoming;

import org.tinylog.Logger;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;

public class ChangeHeadingPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        byte heading = buffer.get();
        Logger.info("Change heading packet received: " + heading);
        return true;
    }

}
