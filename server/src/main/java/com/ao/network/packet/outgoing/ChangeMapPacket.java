package com.ao.network.packet.outgoing;

import com.ao.model.map.WorldMap;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user to load a different map.
 */

public record ChangeMapPacket(WorldMap map) implements OutgoingPacket {

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort((short) map.getId());
        buffer.putShort(map.getVersion());
    }

}
