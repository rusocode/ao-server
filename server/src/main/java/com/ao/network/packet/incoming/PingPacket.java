package com.ao.network.packet.incoming;

import com.ao.model.fonts.Font;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ConsoleMessagePacket;
import com.ao.network.packet.outgoing.PongPacket;

public class PingPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) {

        connection.send(new ConsoleMessagePacket("[SERVIDOR] Ping packet received", Font.SERVER));

        connection.send(new PongPacket());

        return true;
    }

}