package com.ao.network.packet.outgoing;

import java.io.UnsupportedEncodingException;

import com.ao.network.DataBuffer;
import com.ao.network.packet.OutgoingPacket;

public class ErrorMessagePacket implements OutgoingPacket {

	protected String message;

	/**
	 * Creates a new error message packet.
	 * @param message The message content.
	 */
	public ErrorMessagePacket(String message) {
		this.message = message;
	}

	@Override
	public void write(DataBuffer buffer) throws UnsupportedEncodingException {
		// TODO: UTF-8?
		buffer.putASCIIString(message);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
