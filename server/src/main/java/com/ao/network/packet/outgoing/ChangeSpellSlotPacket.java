package com.ao.network.packet.outgoing;

import com.ao.model.spell.Spell;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the client to change a spell's slot.
 */

public record ChangeSpellSlotPacket(Spell spell, byte slot) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(slot);
        if (spell == null) {
            buffer.putShort((short) 0);
            buffer.putASCIIString("(None)");
        } else {
            buffer.putShort((short) spell.getId());
            buffer.putASCIIString(spell.getName());
        }
    }

}
