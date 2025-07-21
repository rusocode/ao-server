
package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

/**
 * Packet to tell the client their new hunger and thirst values
 *
 * @author mvanotti
 */
public class UpdateHungerAndThirstPacket implements OutgoingPacket {
	private final byte minHunger;
	private final byte maxHunger;
	private final byte minThirst;
	private final byte maxThirst;


	/**
	 * Creates a new UpdateHungerAndThirstPacket
	 *
	 * @param minHunger user's current hunger level
	 * @param maxHunger user's total hunger
	 * @param minThirst user's current thirst level
	 * @param maxThirst user's total thirst
	 */
	public UpdateHungerAndThirstPacket(final int minHunger, final int maxHunger,
			final int minThirst, final int maxThirst) {
		this.minHunger = (byte) minHunger;
		this.maxHunger = (byte) maxHunger;
		this.minThirst = (byte) minThirst;
		this.maxThirst = (byte) maxThirst;
	}

	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		// TODO : Is it really necessary to send the max values if they are constant?
		buffer.put(maxThirst);
		buffer.put(minThirst);
		buffer.put(maxHunger);
		buffer.put(minHunger);
	}

}
