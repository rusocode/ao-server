package com.ao.model.user;

import java.util.Set;

public interface Account {

    String getName();

    String getMail();

    Set<String> getCharacters();

    /**
     * Checks if a character with the given name exists in this account. Check is case-insensitive.
     *
     * @param name name of the character for which to check
     * @return true if the character exists in the account, false otherwise
     */
    boolean hasCharacter(String name);

    boolean isBanned();

    void setBanned(boolean banned);

    /**
     * Try to authenticate the account with the given password.
     *
     * @param password password used to authenticate
     * @return true if the password matchs the account password, false otherwise
     */
    boolean authenticate(String password);

    /**
     * Adds another character to the account.
     *
     * @param name character's name
     */
    void addCharacter(String name);

}