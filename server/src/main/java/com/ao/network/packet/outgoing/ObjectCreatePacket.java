package com.ao.network.packet.outgoing;

import com.ao.model.object.Object;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public record ObjectCreatePacket(Object object, byte x, byte y) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(x);
        buffer.put(y);
        buffer.putShort((short) object.getGraphic());
    }

}
