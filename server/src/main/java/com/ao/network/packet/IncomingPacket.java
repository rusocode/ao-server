package com.ao.network.packet;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;

import java.io.UnsupportedEncodingException;

public interface IncomingPacket {

    /**
     * Handles the received packet.
     *
     * @param buffer     buffer from which to read data
     * @param connection packet's connection container
     * @return true if the packet was complete, false otherwise
     * @throws IndexOutOfBoundsException    if not enough data in the buffer
     * @throws UnsupportedEncodingException if a string whose encoding it unknown
     */
    boolean handle(DataBuffer buffer, Connection connection) throws IndexOutOfBoundsException, UnsupportedEncodingException;

}
