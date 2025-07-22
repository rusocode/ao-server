package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class GuildChatPacket implements OutgoingPacket {

    private final String message;

    GuildChatPacket(String message) {
        this.message = message;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putASCIIString(message);
    }

}
