
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Packet to tell the client their new strength and dexterity values
 * @author mvanotti
 */
public class UpdateStrengthAndDexterityPacket implements OutgoingPacket {
	protected byte strength;
	protected byte dexterity;

	/**
	 * Creates a new UpdateStrengthAndDexterity packet
	 *
	 * @param strength the user's strength
	 * @param dexterity the user's dexterity
	 */
	public UpdateStrengthAndDexterityPacket(byte strength, byte dexterity) {
		super();
		this.strength = strength;
		this.dexterity = dexterity;
	}

	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.put(strength);
		buffer.put(dexterity);
	}

}
