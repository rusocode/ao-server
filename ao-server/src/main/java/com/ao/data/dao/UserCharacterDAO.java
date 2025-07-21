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
	 * Return the character with the given name
	 * @param user The connected user loading the character
	 * @param name Character's name
	 * @return
	 * @throws DAOException
	 */
	UserCharacter load(ConnectedUser user, String name) throws DAOException;

	/**
	 * Creates and persists a new user character.
	 * @param user						The connected user creating the character.
	 * @param race 						The character's race.
	 * @param gender 					The character's gender.
	 * @param archetype 				The character's archetype.
	 * @param head	 					The character's head.
	 * @param homeland 					The character's homeland.
	 * @param strength					The character's strength attribute.
	 * @param dexterity					The character's dexterity attribute.
	 * @param intelligence				The character's intelligence attribute.
	 * @param charisma					The character's charisma attribute.
	 * @param constitution				The character's constitution attribute.
	 * @param initialAvailableSkills 	The character's initial available skills
	 * @param body					 	The character's body
	 * @return 							The created user character.
	 */
	UserCharacter create(ConnectedUser user, String name, Race race, Gender gender,
			UserArchetype archetype, int head, City homeland, byte strength,
			byte dexterity, byte intelligence, byte charisma, byte constitution,
			int initialAvailableSkills, int body)
			throws DAOException, NameAlreadyTakenException;

	/**
	 * Checks if the character with the given name exists.
	 * @param name The character name.
	 * @return True if the character exists, false otherwise.
	 */
	boolean exists(String name);
}
