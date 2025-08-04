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
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserDAOIniTest {

    private static final String CHARACTER_NICK = "test";
    private static final String NEW_CHARACTER_NICK = "newchartest";
    private static final String CHARACTER_MAIL = "test@test.com";
    private static final String CHARACTER_PASSWORD = "testpass";
    private static final String CHARFILES_PATH = "charfiles/";
    private UserDAOIni dao;

    @BeforeEach
    public void setUp() throws Exception {
        // Disable escaping for Ini4j
        System.setProperty("org.ini4j.config.escape", "false");
        String charfilesPath = ApplicationProperties.getProperties().getProperty("config.path.charfiles");
        dao = new UserDAOIni(charfilesPath);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Be really sure the file is not there before the next test
        final File file = new File(dao.getCharFilePath(NEW_CHARACTER_NICK));
        file.delete();
    }

    @Test
    public void testRetrieve() throws DAOException {
        final Account acc = dao.retrieve(CHARACTER_NICK);
        assertThat(acc).isNotNull();
        // Ensure we get the requested character and not another one
        assertThat(acc.getName()).isEqualTo(CHARACTER_NICK);
    }

    @Test
    public void testCreateAccount() throws DAOException {
        final Account acc = dao.create(NEW_CHARACTER_NICK, CHARACTER_PASSWORD, CHARACTER_MAIL);

        assertThat(acc.getName()).isEqualTo(NEW_CHARACTER_NICK);
        assertThat(acc.getMail()).isEqualTo(CHARACTER_MAIL);

        assertThat(acc.authenticate(CHARACTER_PASSWORD)).isTrue();

        final File file = new File(dao.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        // Don't leave the file there!
        file.delete();
    }

    @Test
    public void testDelete() throws DAOException {
        dao.create(NEW_CHARACTER_NICK, CHARACTER_PASSWORD, CHARACTER_MAIL);
        final File file = new File(dao.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        dao.delete(NEW_CHARACTER_NICK);

        assertThat(file.exists()).isFalse();
    }

    @Test
    public void testCreateCharacter() throws DAOException {

        final byte[] skills = new byte[Skill.AMOUNT];

        for (int i = 0; i < Skill.AMOUNT; i++) {
            if (i == 1) skills[i] = 10;
            else skills[i] = 0;
        }

        final City city = mock(City.class);

        // TODO Use constants!!
        final UserCharacter chara = dao.create(mock(ConnectedUser.class), NEW_CHARACTER_NICK, Race.HUMAN, Gender.FEMALE,
                UserArchetype.ASSASIN, 75, city, (byte) 18, (byte) 18,
                (byte) 18, (byte) 18, (byte) 18, 10, 1);

        final File file = new File(dao.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        assertThat(chara.getName()).isEqualTo(NEW_CHARACTER_NICK);
        assertThat(chara.getArchetype()).isEqualTo(UserArchetype.ASSASIN.getArchetype());
        assertThat(chara.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(chara.getRace()).isEqualTo(Race.HUMAN);
        assertThat(chara.getLevel()).isEqualTo((byte) 1);

        // TODO To be continued... :P

        file.delete();

    }

}
