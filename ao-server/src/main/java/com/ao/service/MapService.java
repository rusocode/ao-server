

package com.ao.service;

import com.ao.model.character.Character;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;

/**
 * Map Service interface.
 * @author jsotuyod
 */
public interface MapService {

	/**
	 * Loads all maps.
	 */
	void loadMaps();

	/**
	 * Retrieves the map with the given id.
	 * @param id The map's id.
	 * @return The loaded map.
	 */
	WorldMap getMap(int id);

	/**
	 * Loads all cities.
	 */
	void loadCities();

	/**
	 * Retrieves the city with the given id.
	 * @param id The City's id.
	 * @return The city.
	 */
	City getCity(byte id);

	/**
	 * Puts a character at the given position
	 * @param chara The character to be put.
	 * @param pos The position where to put the character.
	 */
	void putCharacterAtPos(Character chara, Position pos);

	/**
	 * Moves the character on the given direction.
	 * @param chara The character to move.
	 * @param heading The heading in which to move.
	 */
	void moveCharacterTo(Character chara, Heading heading);
}
