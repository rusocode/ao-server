package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.model.map.Position;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Tells the user that the visible area in the map changed.
 * @author Juan Martín Sotuyo Dodero
 */
public class AreaChangedPacket implements OutgoingPacket {

	private final Position pos;

	/**
	 * Creates a new AreaChangedPacket.
	 * @param pos The current position of the character.
	 */
	public AreaChangedPacket(final Position pos) {
		this.pos = pos;
	}

	@Override
	public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.put(pos.getX());
		buffer.put(pos.getY());
	}
}
