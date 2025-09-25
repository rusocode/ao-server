package com.ao.data.dao.ini;

import com.ao.context.ApplicationProperties;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.Skill;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.user.Account;
import com.ao.model.user.ConnectedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserDAOIniTest {

    private static final String CHARACTER_NICK = "test"; // TODO No seria mejor CHARACTER_NAME O CHARACTER_USERNAME o USERNAME?
    private static final String NEW_CHARACTER_NICK = "newtest";
    private static final String CHARACTER_MAIL = "test@test.com";
    private static final String CHARACTER_PASSWORD = "testpass";
    private UserDAOIni userDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        String charfilesPath = ApplicationProperties.getProperties().getProperty("config.path.charfiles");
        userDAOIni = new UserDAOIni(charfilesPath);
    }

    // Be sure the file is not there before the next test
    @AfterEach
    public void tearDown() throws Exception {
        Path path = Path.of(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));
        try {
            // Delete the file after testing
            Files.delete(path);
        } catch (NoSuchFileException ignored) {
        }
    }

    @Test
    public void testCreateAccount() throws DAOException {
        Account account = userDAOIni.create(NEW_CHARACTER_NICK, CHARACTER_PASSWORD, CHARACTER_MAIL);

        assertThat(account.getName()).isEqualTo(NEW_CHARACTER_NICK);
        assertThat(account.getMail()).isEqualTo(CHARACTER_MAIL);

        assertThat(account.authenticate(CHARACTER_PASSWORD)).isTrue();

        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

    }

    @Test
    public void testGet() throws DAOException {
        Account account = userDAOIni.get(CHARACTER_NICK);
        assertThat(account).isNotNull();
        // Ensure the correct character is retrieved
        assertThat(account.getName()).isEqualTo(CHARACTER_NICK);
    }

    @Test
    public void testDelete() throws DAOException {
        userDAOIni.create(NEW_CHARACTER_NICK, CHARACTER_PASSWORD, CHARACTER_MAIL);
        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        userDAOIni.delete(NEW_CHARACTER_NICK);

        assertThat(file.exists()).isFalse();
    }

    @Test
    public void testCreateCharacter() throws DAOException {

        byte[] skills = new byte[Skill.values().length];

        for (int i = 0; i < skills.length; i++) {
            if (i == 1) skills[i] = 10;
            else skills[i] = 0;
        }

        City city = mock(City.class);

        // TODO Use constants!!
        UserCharacter userCharacter = userDAOIni.create(mock(ConnectedUser.class), NEW_CHARACTER_NICK, Race.HUMAN, Gender.FEMALE, UserArchetype.ASSASIN,
                75, city, (byte) 18, (byte) 18, (byte) 18, (byte) 18, (byte) 18, 10, 1);

        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        assertThat(userCharacter.getName()).isEqualTo(NEW_CHARACTER_NICK);
        assertThat(userCharacter.getArchetype()).isEqualTo(UserArchetype.ASSASIN.getArchetype());
        assertThat(userCharacter.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(userCharacter.getRace()).isEqualTo(Race.HUMAN);
        assertThat(userCharacter.getLevel()).isEqualTo((byte) 1);

        // TODO To be continued... :P

    }

}
