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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserDAOIniTest {

    private static final String CHARACTER_NICK = "test";
    private static final String NEW_CHARACTER_NICK = "newchartest";
    private static final String CHARACTER_MAIL = "test@test.com";
    private static final String CHARACTER_PASSWORD = "testpass";
    private UserDAOIni userDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        String charfilesPath = ApplicationProperties.getProperties().getProperty("config.path.charfiles");
        userDAOIni = new UserDAOIni(charfilesPath);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Be really sure the file is not there before the next test
        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));
        file.delete(); // Delete the file after testing
    }

    @Test
    public void testRetrieve() throws DAOException {
        Account account = userDAOIni.retrieve(CHARACTER_NICK);
        assertThat(account).isNotNull();
        // Ensure we get the requested character and not another one
        assertThat(account.getName()).isEqualTo(CHARACTER_NICK);
    }

    @Test
    public void testCreateAccount() throws DAOException {
        Account account = userDAOIni.create(NEW_CHARACTER_NICK, CHARACTER_PASSWORD, CHARACTER_MAIL);

        assertThat(account.getName()).isEqualTo(NEW_CHARACTER_NICK);
        assertThat(account.getMail()).isEqualTo(CHARACTER_MAIL);

        assertThat(account.authenticate(CHARACTER_PASSWORD)).isTrue();

        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));

        assertThat(file.exists()).isTrue();

        // Don't leave the file there!
        // file.delete();
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

        byte[] skills = new byte[Skill.AMOUNT];

        for (int i = 0; i < Skill.AMOUNT; i++) {
            if (i == 1) skills[i] = 10;
            else skills[i] = 0;
        }

        City city = mock(City.class);

        // TODO Use constants!!
        UserCharacter chara = userDAOIni.create(mock(ConnectedUser.class), NEW_CHARACTER_NICK, Race.HUMAN, Gender.FEMALE,
                UserArchetype.ASSASIN, 75, city, (byte) 18, (byte) 18,
                (byte) 18, (byte) 18, (byte) 18, 10, 1);

        File file = new File(userDAOIni.getCharFilePath(NEW_CHARACTER_NICK));

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
