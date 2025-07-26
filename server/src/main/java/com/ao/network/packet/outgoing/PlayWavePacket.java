package com.ao.network.packet.outgoing;

import com.ao.model.map.Position;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class PlayWavePacket implements OutgoingPacket {

    private final int wave;
    private final Position position;

    /**
     * Creates PlayWavePacket.
     *
     * @param waveIndex wave index
     * @param position  position of the map where the sound comes from
     */
    PlayWavePacket(int waveIndex, Position position) {
        this.wave = waveIndex;
        this.position = position;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put((byte) wave);
        buffer.put((byte) position.getX());
        buffer.put((byte) position.getY());
    }

}
