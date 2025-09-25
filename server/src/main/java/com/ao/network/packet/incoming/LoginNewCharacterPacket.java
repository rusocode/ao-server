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

public class LoginNewCharacterPacket implements IncomingPacket {

    private static final LoginService service = ApplicationContext.getInstance(LoginService.class);
    private static final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        int incomingBytes = buffer.getReadableBytes();
        int requiredBytes = 12 + security.getPasswordHashLength() + security.getClientHashLength(); // TODO Que es 12?

        System.err.println("Incoming bytes: " + incomingBytes);
        System.err.println("Required bytes: " + requiredBytes);

        // Ensure enough data is available to read
        if (incomingBytes < requiredBytes) {
            System.err.println("Not enough data to read!");
            return false;
        }

        System.err.println("Processing new character login...");

        String nick = buffer.getASCIIString();

        System.out.println("nick: " + nick);

        String password = buffer.getASCIIStringFixed(security.getPasswordHashLength());

        System.out.println("password: " + password);

        // FIXME On the login these are shorts
        String version = buffer.get() + "." + buffer.get() + "." + buffer.get();

        System.out.println("version: " + version);

        String clientHash = buffer.getASCIIStringFixed(security.getClientHashLength());

        System.out.println("clientHash: " + clientHash);

        byte race = buffer.get();
        byte gender = buffer.get();
        byte archetype = buffer.get();
        int head = buffer.get();
        String mail = buffer.getASCIIString();
        byte homeland = buffer.get();

        System.out.println("race:" + race + " gender:" + gender + " archetype:" + archetype + " head:" + head + " mail:" + mail + " homeland:" + homeland);

        try {
            service.connectNewCharacter((ConnectedUser) connection.getUser(), nick, password, race, gender, archetype, head, mail, homeland, clientHash, version);
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
        connection.disconnect();
    }

}
