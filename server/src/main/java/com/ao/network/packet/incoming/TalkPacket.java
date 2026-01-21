package com.ao.network.packet.incoming;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;

import java.io.UnsupportedEncodingException;

public class TalkPacket implements IncomingPacket {

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws IndexOutOfBoundsException, UnsupportedEncodingException {
        return false;
    }

}
