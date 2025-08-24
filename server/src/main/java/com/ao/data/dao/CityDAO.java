package com.ao.data.dao;

import com.ao.model.map.City;

/**
 * DAO for Cities.
 */

public interface CityDAO {

    /**
     * Loads all Cities.
     *
     * @return the array of cities
     */
    City[] loadAll(); // TODO DAOException?

}