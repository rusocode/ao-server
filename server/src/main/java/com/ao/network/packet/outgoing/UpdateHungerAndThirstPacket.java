package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Packet to tell the client their new hunger and thirst values.
 */

public record UpdateHungerAndThirstPacket(int minHunger, int maxHunger, int minThirst, int maxThirst) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        // TODO Is it really necessary to send the max values if they are constant?
        buffer.put((byte) maxThirst);
        buffer.put((byte) minThirst);
        buffer.put((byte) maxHunger);
        buffer.put((byte) minHunger);
    }

}
