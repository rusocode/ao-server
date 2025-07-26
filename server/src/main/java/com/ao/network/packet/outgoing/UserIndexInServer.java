package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client his user index in the server.
 * <p>
 * TODO Is this packet needed at all? The user doesn't us the user index at all, and our server doesn't use user indexes anymore
 */

public class UserIndexInServer implements OutgoingPacket {

    private final short userIndex;

    /**
     * Creates a new UserIndexInServer
     *
     * @param userIndex index to tell to the client
     */
    public UserIndexInServer(final short userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(userIndex);
    }

}
