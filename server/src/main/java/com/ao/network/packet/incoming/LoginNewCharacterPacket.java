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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class LoginNewCharacterPacket implements IncomingPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginNewCharacterPacket.class);

    private static final LoginService service = ApplicationContext.getInstance(LoginService.class);
    private static final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);

    private static final int STRING_LENGTH_BYTES = 2;
    private static final int MIN_NICK_BYTES = STRING_LENGTH_BYTES + 1;
    private static final int MIN_MAIL_BYTES = STRING_LENGTH_BYTES + 1;
    private static final int VERSION_BYTES = 3;
    private static final int RACE_BYTES = 1;
    private static final int GENDER_BYTES = 1;
    private static final int ARCHETYPE_BYTES = 1;
    private static final int HEAD_BYTES = 1;
    private static final int CITY_ID_BYTES = 1;

    @Override
    public boolean handle(DataBuffer buffer, Connection connection) throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        if (!isValidBufferSize(buffer)) return false;

        String nick = buffer.getUTF8String();
        String password = buffer.getUTF8StringFixed(security.getPasswordHashLength());
        String version = buffer.get() + "." + buffer.get() + "." + buffer.get(); // FIXME On the login these are shorts
        String clientHash = buffer.getUTF8StringFixed(security.getClientHashLength());
        byte raceId = buffer.get();
        byte genderId = buffer.get();
        byte archetypeId = buffer.get();
        int headId = buffer.get();
        String mail = buffer.getUTF8String();
        byte cityId = buffer.get();

        LOGGER.info("nick={}, password={}, version={}, clientHash={}, raceId={}, genderId={}, archetypeId={}, headId={}, mail={}, cityId={}", nick, password, version, clientHash, raceId, genderId, archetypeId, headId, mail, cityId);

        try {
            service.connectNewCharacter((ConnectedUser) connection.getUser(), nick, password, raceId, genderId, archetypeId, headId, mail, cityId, clientHash, version);
        } catch (LoginErrorException e) {
            connection.send(new ErrorMessagePacket(e.getMessage()));
            connection.disconnect();
            return false;
        }

        return true;
    }

    private boolean isValidBufferSize(DataBuffer buffer) {
        int incomingBytes = buffer.getReadableBytes();
        int minRequiredBytes = calculateMinRequiredBytes();

        LOGGER.debug("incomingBytes={}, minRequiredBytes={}", incomingBytes, minRequiredBytes);

        if (incomingBytes < minRequiredBytes) {
            LOGGER.error("Not enough bytes to read!");
            return false;
        }

        return true;
    }

    private int calculateMinRequiredBytes() {
        return MIN_NICK_BYTES
                + security.getPasswordHashLength()
                + VERSION_BYTES
                + security.getClientHashLength()
                + RACE_BYTES
                + GENDER_BYTES
                + ARCHETYPE_BYTES
                + HEAD_BYTES
                + MIN_MAIL_BYTES
                + CITY_ID_BYTES;
    }

}
