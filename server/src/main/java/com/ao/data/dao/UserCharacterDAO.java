
package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.user.Account;
import com.ao.model.user.ConnectedUser;

/**
 * <b>Data Access Interface</b> that defines <b>HOW</b> a user character is <b>PERSISTED</b>.
 * <p>
 * In an analogy, this class would be like the "garaje" and the connection {@code UserCharacter} is the "car".
 */
public interface UserCharacterDAO {

    /**
     * Return the character with the given name.
     *
     * @param user connected user loading the character
     * @param name character's name
     * @return UserCharacter
     */
    UserCharacter load(ConnectedUser user, String name) throws DAOException;

    /**
     * Creates a new user character with the specified attributes.
     *
     * @deprecated Use {@link #createAccountAndCharacter} instead for atomic account and character creation
     */
    @Deprecated
    UserCharacter create(ConnectedUser user, String name, String password, String mail, Race race, Gender gender,
                         UserArchetype archetype, int head, City city, byte strength, byte dexterity,
                         byte intelligence, byte charisma, byte constitution,
                         int initialAvailableSkills, int body) throws DAOException, NameAlreadyTakenException;

    AccountAndCharacter createAccountAndCharacter(
            ConnectedUser user, String name, String password, String mail,
            Race race, Gender gender, UserArchetype archetype, int head,
            City city, byte strength, byte dexterity, byte intelligence,
            byte charisma, byte constitution, int initialAvailableSkills, int body
    ) throws DAOException;

    boolean exists(String username);

    /**
     * Record that holds both an Account and a UserCharacter.
     * Used to return both entities from {@link #createAccountAndCharacter}.
     */
    record AccountAndCharacter(Account account, UserCharacter character) {}

}