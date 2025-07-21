
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.model.character.Character;
import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Tells the user that a character is / is not visible.
 * @author Juan Martín Sotuyo Dodero
 */
public class SetInvisiblePacket implements OutgoingPacket {
	private final Character character;
	private final boolean invisible;

	/**
	 * Creates a new SetInvisiblePacket.
	 * @param character The character being shown / hidden.
	 * @param invisible True if the char is invisible, false otherwise
	 */
	public SetInvisiblePacket(final Character character, final boolean invisible) {
		this.character = character;
		this.invisible = invisible;
	}

	@Override
	public void write(final DataBuffer buffer) throws UnsupportedEncodingException {
		buffer.putShort(character.getCharIndex());
		buffer.putBoolean(invisible);
	}
}
