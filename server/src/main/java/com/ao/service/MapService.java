package com.ao.service;

import com.ao.model.character.Character;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;

/**
 * Map Service interface.
 */

public interface MapService {

    /**
     * Loads all maps.
     */
    void loadMaps();

    /**
     * Retrieves the map with the given id.
     *
     * @param id map's id
     * @return the loaded map
     */
    WorldMap getMap(int id);

    /**
     * Loads all cities.
     */
    void loadCities();

    /**
     * Retrieves the city with the given id.
     *
     * @param id city's id
     * @return the city
     */
    City getCity(byte id);

    /**
     * Puts a character at the given position.
     *
     * @param chara character to be put
     * @param pos   position where to put the character
     */
    void putCharacterAtPos(Character chara, Position pos);

    /**
     * Moves the character in the given direction.
     *
     * @param chara   character to move
     * @param heading heading in which to move
     */
    void moveCharacterTo(Character chara, Heading heading);

}
