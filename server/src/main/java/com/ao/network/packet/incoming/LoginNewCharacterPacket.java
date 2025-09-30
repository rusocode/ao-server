package com.ao.network.packet.incoming;

import com.ao.context.ApplicationContext;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ErrorMessagePacket;
import com.ao.security.SecurityManager;
import com.ao.service.LoginService;
import com.ao.service.login.LoginErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static com.ao.service.login.LoginServiceImpl.INVALID_GENDER_ERROR;
import static com.ao.service.login.LoginServiceImpl.INVALID_RACE_ERROR;

public class LoginNewCharacterPacket implements IncomingPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginNewCharacterPacket.class);

    private static final LoginService service = ApplicationContext.getInstance(LoginService.class);
    private static final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        int incomingBytes = buffer.getReadableBytes();

        // Calcular bytes requeridos:
        // - nick: minimo 2 bytes (longitud) + contenido
        // - password: longitud fija conocida
        // - version: 3 bytes
        // - client hash: longitud fija conocida
        // - race, gender, archetype: 3 bytes
        // - head: 4 bytes
        // - mail: minimo 2 bytes (longitud) + contenido
        // - cityId: 1 byte
        int minRequiredBytes = 2 + 1 + security.getPasswordHashLength() + 3 + security.getClientHashLength() + 3 + 4 + 2 + 1 + 1;

        LOGGER.info("incomingBytes={}, minRequiredBytes={}", incomingBytes, minRequiredBytes);

        // Ensure enough data is available to read
        if (incomingBytes < minRequiredBytes) {
            LOGGER.error("Not enough bytes to read!");
            return false;
        }

        LOGGER.info("Processing character data...");

        String nick = buffer.getUTF8String();
        String password = buffer.getUTF8StringFixed(security.getPasswordHashLength());
        String version = buffer.get() + "." + buffer.get() + "." + buffer.get(); // FIXME On the login these are shorts
        String clientHash = buffer.getUTF8StringFixed(security.getClientHashLength());
        byte race = buffer.get();
        byte gender = buffer.get();
        byte archetype = buffer.get();
        int head = buffer.get();
        String mail = buffer.getUTF8String();
        byte cityId = buffer.get();

        Race characterRace = mapRaceFromClientId(race);
        Gender characterGender = mapGenderFromClientId(gender);

        LOGGER.info("nick={}, password={}, version={}, clientHash={}, race={}, gender={}, archetype={}, head={}, mail={}, cityId={}", nick, password, version, clientHash, race, gender, archetype, head, mail, cityId);

        try {
            service.connectNewCharacter((ConnectedUser) connection.getUser(), nick, password, characterRace, characterGender, archetype, head, mail, cityId, clientHash, version);
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

    /**
     * Mapea el ID de raza del cliente al enum Race del servidor
     */
    private Race mapRaceFromClientId(byte clientRaceId) {
        switch (clientRaceId) {
            case 1:
                return Race.HUMAN;      // Cliente: HUMAN(1)
            case 2:
                return Race.ELF;        // Cliente: ELF(2)
            case 3:
                return Race.DARK_ELF;   // Cliente: DROW_ELF(3)
            case 4:
                return Race.GNOME;      // Cliente: GNOME(4)
            case 5:
                return Race.DWARF;      // Cliente: DWARF(5)
            default:
                try {
                    throw new LoginErrorException(INVALID_RACE_ERROR);
                } catch (LoginErrorException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    /**
     * Mapea el ID de género del cliente al enum Gender del servidor
     */
    private Gender mapGenderFromClientId(byte clientGenderId) {
        switch (clientGenderId) {
            case 1:
                return Gender.MALE;     // Cliente: MALE (índice 0, envía 1)
            case 2:
                return Gender.FEMALE;   // Cliente: FEMALE (índice 1, envía 2)
            default:
                try {
                    throw new LoginErrorException(INVALID_GENDER_ERROR);
                } catch (LoginErrorException e) {
                    throw new RuntimeException(e);
                }
        }
    }

}
