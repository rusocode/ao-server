package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class ErrorMessagePacket implements OutgoingPacket {

    protected String message;

    /**
     * Creates a new error message packet.
     */
    public ErrorMessagePacket(String message) {
        this.message = message;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        // TODO UTF-8?
        buffer.putASCIIString(message);
    }

    public String getMessage() {
        return message;
    }

}
