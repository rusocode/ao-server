package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.map.City;
import com.ao.model.user.ConnectedUser;

/**
 * A DAO for user characters.
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
     * Creates and persists a new user character.
     *
     * @param user                   connected user creating the character
     * @param race                   character's race
     * @param gender                 character's gender
     * @param archetype              character's archetype
     * @param head                   character's head
     * @param homeland               character's homeland
     * @param strength               character's strength attribute
     * @param dexterity              character's dexterity attribute
     * @param intelligence           character's intelligence attribute
     * @param charisma               character's charisma attribute
     * @param constitution           character's constitution attribute
     * @param initialAvailableSkills character's initial available skills
     * @param body                   character's body
     * @return the created user character
     */
    UserCharacter create(ConnectedUser user, String name, Race race, Gender gender,
                         UserArchetype archetype, int head, City homeland, byte strength,
                         byte dexterity, byte intelligence, byte charisma, byte constitution,
                         int initialAvailableSkills, int body)
            throws DAOException, NameAlreadyTakenException;

    /**
     * Checks if the character with the given name exists.
     *
     * @param name character name
     * @return true if the character exists, false otherwise
     */
    boolean exists(String name);

}
