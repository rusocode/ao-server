package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class PlayMidiPacket implements OutgoingPacket {

    private final int midi;
    private final int loops;

    /**
     * Creates a PlayMidiPacket.
     *
     * @param midiIndex index of the midi sound
     * @param loops     number of times to be played. -1 for infinite time
     */
    public PlayMidiPacket(int midiIndex, int loops) {
        this.midi = midiIndex;
        this.loops = loops;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort((short) midi);
        buffer.putShort((short) loops);
    }

}
