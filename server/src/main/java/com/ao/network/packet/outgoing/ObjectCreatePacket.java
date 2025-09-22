package com.ao.network.packet.outgoing;

import com.ao.model.worldobject.Object;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

public class ObjectCreatePacket implements OutgoingPacket {

    private final Object object;
    private final byte posX;
    private final byte posY;

    public ObjectCreatePacket(Object object, byte posX, byte posY) {
        this.object = object;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(posX);
        buffer.put(posY);
        buffer.putShort((short) object.getGraphic());
    }

}
