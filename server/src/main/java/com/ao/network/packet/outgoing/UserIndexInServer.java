package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client his user index in the server.
 * <p>
 * TODO Is this packet needed at all? The user doesn't us the user index at all, and our server doesn't use user indexes anymore
 */

public record UserIndexInServer(short userIndex) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(userIndex);
    }

}
