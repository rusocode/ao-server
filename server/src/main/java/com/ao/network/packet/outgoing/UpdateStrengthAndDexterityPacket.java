package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client their new strength and dexterity values.
 */

public class UpdateStrengthAndDexterityPacket implements OutgoingPacket {

    protected byte strength, dexterity;

    /**
     * Creates a new UpdateStrengthAndDexterity packet
     *
     * @param strength  the user's strength
     * @param dexterity the user's dexterity
     */
    public UpdateStrengthAndDexterityPacket(byte strength, byte dexterity) {
        super();
        this.strength = strength;
        this.dexterity = dexterity;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(strength);
        buffer.put(dexterity);
    }

}
