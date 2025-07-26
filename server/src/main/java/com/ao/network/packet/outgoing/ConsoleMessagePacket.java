package com.ao.network.packet.outgoing;

import com.ao.model.fonts.Font;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class ConsoleMessagePacket implements OutgoingPacket {

    private final String message;
    private final Font font;

    /**
     * Creates a console message.
     */
    public ConsoleMessagePacket(String message, Font font) {
        this.message = message;
        this.font = font;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putASCIIString(message);
        buffer.put((byte) font.ordinal());
    }

}
