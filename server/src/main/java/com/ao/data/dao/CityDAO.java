package com.ao.data.dao;

import com.ao.model.map.City;

/**
 * DAO for Cities.
 */

public interface CityDAO {

    /**
     * Loads all Cities.
     *
     * @return the complete list of Cities positions
     */
    City[] retrieveAll();

}