package com.ao.network.packet.outgoing;

import com.ao.model.map.WorldMap;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user to load a different map.
 */

public class ChangeMapPacket implements OutgoingPacket {

    private final WorldMap map;

    /**
     * Creates a new ChangeMapPacket.
     *
     * @param map map to be loaded by the client
     */
    public ChangeMapPacket(WorldMap map) {
        this.map = map;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.putShort((short) map.getId());
        buffer.putShort(map.getVersion());
    }

}
