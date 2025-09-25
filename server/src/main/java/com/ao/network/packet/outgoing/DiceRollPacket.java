package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record DiceRollPacket(byte strength, byte dexterity, byte intelligence, byte charisma,
                             byte constitution) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(strength);
        buffer.put(dexterity);
        buffer.put(intelligence);
        buffer.put(charisma);
        buffer.put(constitution);
    }

}
