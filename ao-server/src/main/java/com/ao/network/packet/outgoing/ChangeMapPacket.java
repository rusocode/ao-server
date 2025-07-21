package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.model.map.WorldMap;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Tells the user to load a different map.
 * @author Brian Chaia
 */
public class ChangeMapPacket implements OutgoingPacket {

	private WorldMap map;

	/**
	 * Creates a new ChangeMapPacket.
	 * @param map The map to be loaded by the client.
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
