package com.ao.network.packet.outgoing;

import com.ao.model.character.Character;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user that a character is / is not visible.
 */

public class SetInvisiblePacket implements OutgoingPacket {

    private final Character character;
    private final boolean invisible;

    /**
     * Creates a new SetInvisiblePacket.
     *
     * @param character character being shown/hidden
     * @param invisible true if the char is invisible, false otherwise
     */
    public SetInvisiblePacket(Character character, boolean invisible) {
        this.character = character;
        this.invisible = invisible;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort(character.getCharIndex());
        buffer.putBoolean(invisible);
    }

}
