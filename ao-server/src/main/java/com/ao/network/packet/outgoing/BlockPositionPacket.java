package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Tells the user that the visible area in the map changed.
 * @author Juan Martín Sotuyo Dodero
 */
public class BlockPositionPacket implements OutgoingPacket {

	private final byte posX;
	private final byte posY;
	private final boolean blocked;

	/**
	 * Creates a new BlockPositionPacket.
	 * @param posX The x coord of the tile to block / unblock
	 * @param posY The y coord of the tile to block / unblock
	 * @param blocked Whether the tile should be blocked or not
	 */
	public BlockPositionPacket(final byte posX, final byte posY, final boolean blocked) {
		this.posX = posX;
		this.posY = posY;
		this.blocked = blocked;
	}

	@Override
	public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.put(posX);
		buffer.put(posY);
		buffer.putBoolean(blocked);
	}
}
