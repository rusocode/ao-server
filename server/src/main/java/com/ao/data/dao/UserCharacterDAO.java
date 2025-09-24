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

    UserCharacter create(ConnectedUser user, String name, Race race, Gender gender, UserArchetype archetype, int head,
                         City homeland, byte strength, byte dexterity, byte intelligence, byte charisma, byte constitution,
                         int initialAvailableSkills, int body) throws DAOException, NameAlreadyTakenException;

    boolean exists(String username);

}
