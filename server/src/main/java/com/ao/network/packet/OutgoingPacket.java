package com.ao.network.packet;

import com.ao.network.DataBuffer;

import java.io.UnsupportedEncodingException;

public interface OutgoingPacket {

    /**
     * Writes the packet in the given buffer.
     *
     * @param buffer buffer to write in
     */
    void write(DataBuffer buffer) throws UnsupportedEncodingException;

}
