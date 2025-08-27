package com.ao.model.user;

import java.util.Set;

/**
 * A user account.
 */

public class AccountImpl implements Account {

    private final String name;
    private final String password;
    private final String mail;
    protected Set<String> characters;
    private boolean banned;

    /**
     * Creates a new account instance.
     *
     * @param name       name of the account
     * @param password   account's password
     * @param mail       account's email
     * @param characters account's characters
     * @param banned     whether the account is banned or not
     */
    public AccountImpl(String name, String password, String mail, Set<String> characters, boolean banned) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.characters = characters;
        this.banned = banned;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public Set<String> getCharacters() {
        return characters;
    }

    @Override
    public boolean hasCharacter(String name) {
        String nameLower = name.toLowerCase();
        for (String character : characters)
            if (nameLower.equals(character.toLowerCase())) return true;
        return false;
    }

    @Override
    public boolean isBanned() {
        return banned;
    }

    @Override
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean authenticate(String password) {
        return this.password.toLowerCase().equals(password.toLowerCase());
    }

    @Override
    public void addCharacter(String name) {
        characters.add(name);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AccountImpl other = (AccountImpl) obj;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}
