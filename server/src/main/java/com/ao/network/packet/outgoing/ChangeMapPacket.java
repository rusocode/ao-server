package com.ao.network.packet.outgoing;

import com.ao.model.map.Map;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user to load a different map.
 */

public record ChangeMapPacket(Map map) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort((short) map.getId());
        buffer.putShort(map.getVersion());
    }

}
