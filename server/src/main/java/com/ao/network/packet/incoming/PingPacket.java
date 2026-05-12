package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.PongPacket;

public class PingPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {
        connection.send(new PongPacket());
        return true;
    }

}