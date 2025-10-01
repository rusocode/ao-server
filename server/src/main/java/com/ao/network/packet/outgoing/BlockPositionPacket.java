package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Sends the block's position and whether is blocked.
 */

public record BlockPositionPacket(byte x, byte y, boolean blocked) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(x);
        buffer.put(y);
        buffer.putBoolean(blocked);
    }

}
