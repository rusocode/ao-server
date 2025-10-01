package com.ao.network.packet.outgoing;

import com.ao.model.map.Position;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record PlayWavePacket(int wave, Position position) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put((byte) wave);
        buffer.put(position.getX());
        buffer.put(position.getY());
    }

}
