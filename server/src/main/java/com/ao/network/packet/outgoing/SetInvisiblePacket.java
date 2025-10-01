package com.ao.network.packet.outgoing;

import com.ao.model.character.Character;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Sends a packet to update a character's visibility state on the client.
 */

public record SetInvisiblePacket(Character character, boolean invisible) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(character.getCharIndex());
        buffer.putBoolean(invisible);
    }

}
