package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client their new strength and dexterity values.
 */

public record UpdateStrengthAndDexterityPacket(byte strength, byte dexterity) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(strength);
        buffer.put(dexterity);
    }

}
