package com.ao.network.packet.incoming;

import com.ao.context.ApplicationContext;
import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ErrorMessagePacket;
import com.ao.security.SecurityManager;
import com.ao.service.LoginService;
import com.ao.service.login.LoginErrorException;

import java.io.UnsupportedEncodingException;

public class LoginExistingCharacterPacket implements IncomingPacket {

    private static final LoginService loginService = ApplicationContext.getInstance(LoginService.class);
    private static final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws IndexOutOfBoundsException, UnsupportedEncodingException {
        // Check if there is enough data to attempt to read...
        if (buffer.getReadableBytes() < 5 + security.getPasswordHashLength() + security.getClientHashLength())
            return false;

        String username = buffer.getASCIIString();
        String password = buffer.getASCIIStringFixed(security.getPasswordHashLength());

        String version = buffer.get() + "." + buffer.get() + "." + buffer.get();
        String clientHash = buffer.getASCIIStringFixed(security.getClientHashLength());

        try {
            loginService.connectExistingCharacter((ConnectedUser) connection.getUser(), username, password, version, clientHash);
        } catch (LoginErrorException e) {
            loginError(connection, e.getMessage());
        }

        return true;
    }

    /**
     * Notifies the client about the error and closes the connection.
     *
     * @param connection connection where the login error has occurred
     * @param cause      error cause
     */
    private void loginError(Connection connection, String cause) throws UnsupportedEncodingException {
        connection.send(new ErrorMessagePacket(cause));
        // TODO Check if the message is sent BEFORE the disconnect or not...
        connection.disconnect();
    }

}
