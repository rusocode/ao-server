package com.ao.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountImplTest {

    private static final String ACCOUNT_NAME = "an account";
    private static final String ACCOUNT_PASSWORD = "a password";
    private static final String ACCOUNT_INVALID_PASSWORD = "invalid pass";
    private static final String ACCOUNT_EMAIL = "anemail@address.com";
    private static final boolean ACCOUNT_BANNED = false;
    private static final Set<String> ACCOUNT_CHARACTERS = new HashSet<>();
    private static final String TEST_CHARACTER_NAME = "TEST";
    private static final String NON_EXISTING_CHARACTER_NAME = "non existing character";

    private AccountImpl account;

    @BeforeEach
    public void setUp() throws Exception {
        account = new AccountImpl(ACCOUNT_NAME, ACCOUNT_PASSWORD, ACCOUNT_EMAIL, ACCOUNT_CHARACTERS, ACCOUNT_BANNED);
        account.characters.add(TEST_CHARACTER_NAME);
    }

    @Test
    public void testGetName() {
        assertThat(ACCOUNT_NAME).isEqualTo(account.getName()); // Se asegura de que el valor actual sea igual al valor esperado
    }

    @Test
    public void testGetMail() {
        assertThat(ACCOUNT_EMAIL).isEqualTo(account.getMail());
    }

    @Test
    public void testGetCharacters() {
        assertThat(ACCOUNT_CHARACTERS).isEqualTo(account.getCharacters());
    }

    @Test
    public void testHasCharacter() {
        assertThat(account.hasCharacter(TEST_CHARACTER_NAME)).isTrue();
        assertThat(account.hasCharacter(NON_EXISTING_CHARACTER_NAME)).isFalse();
    }

    @Test
    public void testIsBanned() {
        assertThat(ACCOUNT_BANNED).isEqualTo(account.isBanned());
        account.setBanned(!ACCOUNT_BANNED);
        assertThat(!ACCOUNT_BANNED).isEqualTo(account.isBanned());
    }

    @Test
    public void testAuthenticate() {
        assertThat(account.authenticate(ACCOUNT_PASSWORD)).isTrue();
        assertThat(account.authenticate(ACCOUNT_INVALID_PASSWORD)).isFalse();
    }

    @Test
    public void testAddCharacter() {
        final String charr = "foo";
        account.addCharacter(charr);
        assertThat(account.characters.contains(charr)).isTrue();
    }

}
