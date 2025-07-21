
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Packet to tell the client his character index in the server.
 * @author Gonzalo Fernández Verón
 */
public class UserCharacterIndexInServerPacket implements OutgoingPacket {

	private short charIndex;

	/**
	 * Create a new UserCharacterIndexInServerPacket
	 * @param charIndex The char inedx to tell to the client.
	 */
	public UserCharacterIndexInServerPacket(short charIndex) {
		super();
		this.charIndex = charIndex;
	}

	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.putShort(charIndex);
	}

}
