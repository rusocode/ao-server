package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client their new dexterity value.
 */

public class UpdateDexterityPacket implements OutgoingPacket {

    protected byte dexterity;

    /**
     * Creates a new UpdateDexterityPacket packet.
     *
     * @param dexterity user's dexterity
     */
    public UpdateDexterityPacket(byte dexterity) {
        super();
        this.dexterity = dexterity;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(dexterity);
    }

}
