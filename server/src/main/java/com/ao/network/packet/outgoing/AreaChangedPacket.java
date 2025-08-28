package com.ao.network.packet.outgoing;

import com.ao.model.map.Position;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user that the visible area in the map changed.
 */

public class AreaChangedPacket implements OutgoingPacket {

    private final Position pos;

    /**
     * Creates a new AreaChangedPacket.
     *
     * @param pos current position of the character
     */
    public AreaChangedPacket(Position pos) {
        this.pos = pos;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(pos.getX());
        buffer.put(pos.getY());
    }

}
