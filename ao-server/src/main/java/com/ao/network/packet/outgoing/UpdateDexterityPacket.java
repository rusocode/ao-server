
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Packet to tell the client their new dexterity value
 * @author mvanotti
 */
public class UpdateDexterityPacket implements OutgoingPacket {
	protected byte dexterity;

	/**
	 * Creates a new UpdateDexterityPacket packet
	 *
	 * @param dexterity the user's dexterity
	 */
	public UpdateDexterityPacket(byte dexterity) {
		super();
		this.dexterity = dexterity;
	}



	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.put(dexterity);
	}

}
