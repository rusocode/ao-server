package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

public class PongPacket implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) {
        buffer.put((byte) 0);
    }

}
