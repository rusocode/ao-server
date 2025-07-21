
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Packet to tell the client their new strength value
 * @author mvanotti
 */
public class UpdateStrengthPacket implements OutgoingPacket {
	protected byte strength;



	/**
	 * Creates a new UpdateStrength packet
	 *
	 * @param strength the user's strength
	 */
	public UpdateStrengthPacket(byte strength) {
		super();
		this.strength = strength;
	}



	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.put(strength);
	}

}
