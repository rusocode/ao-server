package com.ao.network.packet.incoming;

import java.io.UnsupportedEncodingException;

import com.ao.context.ApplicationContext;
import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ErrorMessagePacket;
import com.ao.security.SecurityManager;
import com.ao.service.LoginService;
import com.ao.service.login.LoginErrorException;

public class LoginNewCharacterPacket implements IncomingPacket {

	private static LoginService service = ApplicationContext.getInstance(LoginService.class);
	private static SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);

	@Override
	public boolean handle(DataBuffer buffer, Connection connection) throws ArrayIndexOutOfBoundsException,
			UnsupportedEncodingException {
		// Check if there is enough data to attempt to read...
		if (buffer.getReadableBytes() < 12 + security.getPasswordHashLength() + security.getClientHashLength()) {
			return false;
		}

		String nick = buffer.getASCIIString();
		String password = buffer.getASCIIStringFixed(security.getPasswordHashLength());

		// FIXME : On the login these are shorts...
		String version = buffer.get() + "." + buffer.get() + "." + buffer.get();
		String clientHash = buffer.getASCIIStringFixed(security.getClientHashLength());


		byte race = buffer.get();
		byte gender = buffer.get();
		byte archetype = buffer.get();
		int head = buffer.get();
		String mail = buffer.getASCIIString();
		byte homeland = buffer.get();

		try {
			service.connectNewCharacter((ConnectedUser) connection.getUser(),
					nick, password, race, gender, archetype, head, mail,
					homeland, clientHash, version);

		} catch (LoginErrorException e) {
			loginError(connection, e.getMessage());
		}

		return true;
	}

	/**
	 * Notifies the client about the error and closes the connection.
	 * @param connection The connection where the login error has occurred.
	 * @param cause 	 The error cause.
	 * @throws UnsupportedEncodingException
	 */
	private void loginError(Connection connection, String cause) throws UnsupportedEncodingException {
		connection.send(new ErrorMessagePacket(cause));
		connection.disconnect();
	}
}
