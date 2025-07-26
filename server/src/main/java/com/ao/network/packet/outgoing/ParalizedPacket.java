package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class ParalizedPacket implements OutgoingPacket {

    @Override
    public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
        // we have no extra data to write
    }

}
