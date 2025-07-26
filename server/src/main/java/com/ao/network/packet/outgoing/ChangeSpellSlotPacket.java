package com.ao.network.packet.outgoing;

import com.ao.model.spell.Spell;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the client to change a spell's slot.
 */

public class ChangeSpellSlotPacket implements OutgoingPacket {

    private final Spell spell;
    private final byte slot;

    /**
     * Creates a new ChangeSpellSlotPacket.
     *
     * @param spell spell to set at the given position (maybe null)
     * @param slot  slot at which to set the item
     */
    public ChangeSpellSlotPacket(final Spell spell, final byte slot) {
        super();
        this.spell = spell;
        this.slot = slot;
    }

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
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
