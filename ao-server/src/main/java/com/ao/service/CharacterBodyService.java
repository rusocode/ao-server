

package com.ao.service;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;

public interface CharacterBodyService {

	/**
	 * Validate the character head
	 * @param head		The character's head
	 * @param race		The character's race
	 * @param gender	The character's gender
	 * @return			True if is valid
	 */
	boolean isValidHead(int head, Race race, Gender gender);
	
	/**
	 * Returns the correct body for the given race and gender
	 * @param race		The character's race
	 * @param gender	The character's gender
	 * @return			The character' body
	 */
	int getBody(Race race, Gender gender);
}