package com.ao.network.packet.incoming;

import com.ao.config.ServerConfig;
import com.ao.context.ApplicationContext;
import com.ao.context.ApplicationProperties;
import com.ao.data.dao.AccountDAO;
import com.ao.mock.MockFactory;
import com.ao.model.builder.UserCharacterBuilder;
import com.ao.model.character.Attribute;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.user.Account;
import com.ao.model.user.ConnectedUser;
import com.ao.network.Connection;
import com.ao.network.DataBuffer;
import com.ao.network.packet.IncomingPacket;
import com.ao.network.packet.outgoing.ErrorMessagePacket;
import com.ao.security.SecurityManager;
import com.ao.service.LoginService;
import com.ao.service.MapService;
import com.ao.service.login.LoginServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LoginNewCharacterPacketTest {

    private static final String CHARACTER_NAME = "test";
    private static final String CHARACTER_PASSWORD = "a";
    private static final String CHARACTER_MAIL = "test@ao.com";
    private static final byte CHARACTER_ARCHETYPE = (byte) UserArchetype.ASSASIN.ordinal();
    private static final byte CHARACTER_RACE = (byte) Race.HUMAN.ordinal();
    private static final byte CHARACTER_GENDER = (byte) Gender.MALE.ordinal();
    private static final byte CHARACTER_HOMELAND = 1;
    private static final byte CHARACTER_HEAD = 10;

    private static final String INVALID_CHARACTER_NAME = " ";

    private static final byte CLIENT_MAJOR = 0;
    private static final byte CLIENT_MINOR = 12;
    private static final byte CLIENT_VERSION = 2;

    static {
        ApplicationProperties.loadProperties("project.properties");
        ApplicationContext.reload();
    }

    private Connection connection;
    private IncomingPacket packet;
    private DataBuffer inputBuffer;
    private ArgumentCaptor<ErrorMessagePacket> errPacket;
    private ServerConfig config;
    private SecurityManager security;

    @BeforeEach
    public void setUp() throws Exception {
        packet = new LoginNewCharacterPacket();
        errPacket = ArgumentCaptor.forClass(ErrorMessagePacket.class);
        connection = MockFactory.mockConnection();

        inputBuffer = mock(DataBuffer.class);

        config = ApplicationContext.getInstance(ServerConfig.class);
        security = ApplicationContext.getInstance(SecurityManager.class);

        MapService mapService = ApplicationContext.getInstance(MapService.class);
        mapService.loadCities();

        config.setRestrictedToAdmins(false);
        config.setCharacterCreationEnabled(true);

    }

    @AfterEach
    public void tearDown() {
        ApplicationContext.getInstance(AccountDAO.class).delete(CHARACTER_NAME);
        ApplicationContext.getInstance(AccountDAO.class).delete(INVALID_CHARACTER_NAME);
    }

    @Test
    public void invalidEmailTest() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, "foo", CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(UserCharacterBuilder.INVALID_EMAIL_ERROR);
    }

    @Test
    public void invalidNameTest() throws Exception {
        writeLogin(INVALID_CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(UserCharacterBuilder.INVALID_NAME_ERROR);
    }

    @Test
    public void successfulCharacterCreation() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);

        ArgumentCaptor<Account> capture = ArgumentCaptor.forClass(Account.class);
        verify((ConnectedUser) connection.getUser()).setAccount(capture.capture());
        Account account = capture.getValue();

        assertThat(account.hasCharacter(CHARACTER_NAME)).isTrue();
        assertThat(account.getNick()).isEqualTo(CHARACTER_NAME);
        assertThat(account.getMail()).isEqualTo(CHARACTER_MAIL);

        // TODO Check if the charfile was created.
    }

    @Test
    public void clientOutOfDateTest() throws Exception {
        LoginServiceImpl service = (LoginServiceImpl) ApplicationContext.getInstance(LoginService.class);
        service.setCurrentClientVersion(CLIENT_MAJOR + "." + CLIENT_MINOR + "." + CLIENT_VERSION);

        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, (byte) 0, (byte) 0,
                (byte) 0, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(String.format(LoginServiceImpl.CLIENT_OUT_OF_DATE_ERROR_FORMAT, CLIENT_MAJOR + "." + CLIENT_MINOR + "." + CLIENT_VERSION));
    }

    @Test
    public void nameTakenTest() throws Exception {
        ApplicationContext.getInstance(AccountDAO.class).create(CHARACTER_NAME, CHARACTER_PASSWORD, CHARACTER_MAIL);

        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.ACCOUNT_NAME_TAKEN_ERROR);
    }

    @Test
    public void dicesThrewTest() throws Exception {
        final ConnectedUser user = (ConnectedUser) connection.getUser();
        when(user.getAttribute(any(Attribute.class))).thenReturn(null);

        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.MUST_THROW_DICES_BEFORE_ERROR);
    }

    @Test
    public void invalidRaceTest() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", (byte) -1, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.INVALID_RACE_ERROR);
    }

    @Test
    public void invalidGenderTest() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, (byte) -1, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.INVALID_GENDER_ERROR);
    }

    @Test
    public void invalidHeadTest() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                (byte) -1, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.INVALID_HEAD_ERROR);
    }

    @Test
    public void invalidArchetypeTest() throws Exception {
        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, (byte) -1,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.INVALID_ARCHETYPE_ERROR);
    }

    @Test
    public void characterCreationDisabledTest() throws Exception {
        config.setCharacterCreationEnabled(false);

        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.CHARACTER_CREATION_DISABLED_ERROR);
    }

    @Test
    public void restrictedToAdminsTest() throws Exception {
        config.setRestrictedToAdmins(true);

        writeLogin(CHARACTER_NAME, CHARACTER_PASSWORD, CLIENT_MAJOR, CLIENT_MINOR,
                CLIENT_VERSION, "", CHARACTER_RACE, CHARACTER_GENDER, CHARACTER_ARCHETYPE,
                CHARACTER_HEAD, CHARACTER_MAIL, CHARACTER_HOMELAND);

        packet.handle(inputBuffer, connection);
        verify(connection).send(errPacket.capture());
        assertThat(errPacket.getValue().getMessage()).isEqualTo(LoginServiceImpl.ONLY_ADMINS_ERROR);
    }

    private void writeLogin(String charName, String password, byte major, byte minor, byte version, String hash, byte race, byte gender, byte archetype, byte head, String mail, byte cityId) throws Exception {
        when(inputBuffer.getReadableBytes()).thenReturn(charName.length() + 2 + security.getPasswordHashLength() + 8 + security.getClientHashLength() + mail.length() + 2);
        when(inputBuffer.getASCIIString()).thenReturn(charName).thenReturn(mail);
        when(inputBuffer.getASCIIStringFixed(security.getPasswordHashLength())).thenReturn(password);
        when(inputBuffer.get()).thenReturn(major).thenReturn(minor).thenReturn(version).thenReturn(race).thenReturn(gender).thenReturn(archetype).thenReturn(head).thenReturn(cityId);
        when(inputBuffer.getASCIIStringFixed(security.getClientHashLength())).thenReturn(hash);
    }

}
