package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client his character index in the server.
 */

public class UserCharacterIndexInServerPacket implements OutgoingPacket {

    private final short charIndex;

    /**
     * Create a new UserCharacterIndexInServerPacket
     *
     * @param charIndex char inedx to tell to the client
     */
    public UserCharacterIndexInServerPacket(short charIndex) {
        super();
        this.charIndex = charIndex;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(charIndex);
    }

}
