package com.ao.network.packet.incoming;

import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.context.ApplicationProperties;
import com.ao.mock.MockFactory;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ErrorMessagePacket;
import com.ao.security.SecurityManager;
import com.ao.service.LoginService;
import com.ao.service.login.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LoginExistingCharacterPacketTest {

    private static final String BANNED_CHARACTER_NAME = "banned";
    private static final String BANNED_CHARACTER_PASSWORD = "a";
    private static final String CHARACTER_NAME = "test";
    private static final String CHARACTER_PASSWORD = "a";
    private static final byte CLIENT_MAJOR = 0;
    private static final byte CLIENT_MINOR = 12;
    private static final byte CLIENT_REVISION = 2;

    static {
        ApplicationProperties.loadProperties("project.properties");
        ApplicationContext.reload();
    }

    private final ServerConfig config = ApplicationContext.getInstance(ServerConfig.class);
    private final SecurityManager security = ApplicationContext.getInstance(SecurityManager.class);
    private Connection connection;
    private IncomingPacket packet;
    private DataBuffer inputBuffer;
    private ArgumentCaptor<ErrorMessagePacket> errPacket;

    @BeforeEach
    public void setUp() throws Exception {
        packet = new LoginExistingCharacterPacket();
        errPacket = ArgumentCaptor.forClass(ErrorMessagePacket.class);
        connection = MockFactory.mockConnection();
        inputBuffer = mock(DataBuffer.class);
        config.setRestrictedToAdmins(false);
    }

    @Test
    public void testHandleRestrictedToAdminsTest() throws Exception {
        config.setRestrictedToAdmins(true);
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR, CLIENT_REVISION, "");
        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.ONLY_ADMINS_ERROR);
    }

    @Test
    public void testHandleCharacterNotFound() throws Exception {
        writeLogin("foo", "foo", CLIENT_MAJOR, CLIENT_MINOR, CLIENT_REVISION, "");
        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.CHARACTER_NOT_FOUND_ERROR);
    }

    @Test
    public void testHandleIncorrectPassword() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD + "foo", CLIENT_MAJOR, CLIENT_MINOR, CLIENT_REVISION, "");
        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.INCORRECT_PASSWORD_ERROR);
    }

    @Test
    public void testHandleOutOfDateClient() throws Exception {
        LoginServiceImpl service = (LoginServiceImpl) ApplicationContext.getInstance(LoginService.class);
        service.setCurrentClientVersion(CLIENT_MAJOR + "." + CLIENT_MINOR + "." + CLIENT_REVISION);
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, (byte) 0, (byte) 0, (byte) 0, "");
        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(String.format(LoginServiceImpl.CLIENT_OUT_OF_DATE_ERROR_FORMAT, CLIENT_MAJOR + "." + CLIENT_MINOR + "." + CLIENT_REVISION));
    }

    @Test
    public void testHandleBannedCharacter() throws Exception {
        writeLogin(BANNED_CHARACTER_NAME, BANNED_CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR, CLIENT_REVISION, "");
        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.BANNED_CHARACTER_ERROR);
    }

    private void writeLogin(final String charName, final String password,
                            final byte major, final byte minor, final byte version,
                            final String hash) throws Exception {
        when(inputBuffer.getReadableBytes()).thenReturn(charName.length() + 2 + security.getPasswordHashLength() + 6 + security.getClientHashLength());
        when(inputBuffer.getASCIIString()).thenReturn(charName);
        when(inputBuffer.getASCIIStringFixed(security.getPasswordHashLength())).thenReturn(password);
        when(inputBuffer.get()).thenReturn(major).thenReturn(minor).thenReturn(version);
        when(inputBuffer.getASCIIStringFixed(security.getClientHashLength())).thenReturn(hash);
    }

}
