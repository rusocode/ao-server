package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class DiceRollPacket implements OutgoingPacket {

    private final byte strength;
    private final byte dexterity;
    private final byte intelligence;
    private final byte charisma;
    private final byte constitution;


    public DiceRollPacket(byte strength, byte dexterity, byte intelligence, byte charisma, byte constitution) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.constitution = constitution;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(strength);
        buffer.put(dexterity);
        buffer.put(intelligence);
        buffer.put(charisma);
        buffer.put(constitution);
    }

}
