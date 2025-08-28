package com.ao.network.packet.outgoing;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

import java.io.UnsupportedEncodingException;

/**
 * Tells the user that the visible area in the map changed.
 */

public class BlockPositionPacket implements OutgoingPacket {

    private final byte posX;
    private final byte posY;
    private final boolean blocked;

    /**
     * Creates a new BlockPositionPacket.
     *
     * @param posX    x coord of the tile to block/unblock
     * @param posY    y coord of the tile to block/unblock
     * @param blocked whether the tile should be blocked or not
     */
    public BlockPositionPacket(byte posX, byte posY, boolean blocked) {
        this.posX = posX;
        this.posY = posY;
        this.blocked = blocked;
    }

    @Override
    public void write(DataBuffer buffer) throws UnsupportedEncodingException {
        buffer.put(posX);
        buffer.put(posY);
        buffer.putBoolean(blocked);
    }

}
