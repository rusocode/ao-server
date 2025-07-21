

package com.ao.network.packet;

import java.io.UnsupportedEncodingException;

import com.ao.network.Connection;
import com.ao.network.DataBuffer;

public interface IncomingPacket {

	/**
	 * Handles the received packet.
	 * @param buffer The buffer from which to read data.
	 * @param connection The packet's connection container.
	 * @return True if the packet was complete, false otherwise.
	 * @throws IndexOutOfBoundsException If there is not enough data in the buffer
	 * @throws UnsupportedEncodingException If there is a string whose encoding it unknown.
	 */
	boolean handle(DataBuffer buffer, Connection connection) throws IndexOutOfBoundsException, UnsupportedEncodingException;
}
