package com.ao.network.packet.outgoing;

import com.ao.model.fonts.Font;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record ConsoleMessagePacket(String message, Font font) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putASCIIString(message);
        buffer.put((byte) font.ordinal());
    }

}
