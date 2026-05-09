package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.map.City;

/**
 * DAO for cities.
 */

public interface CityDAO {

    /**
     * Loads all cities.
     *
     * @return the array of cities
     */
    City[] load() throws DAOException;

}