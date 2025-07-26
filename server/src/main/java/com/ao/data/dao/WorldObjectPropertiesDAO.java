package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.ao.model.worldobject.properties.manufacture.Manufacturable;

import java.util.Map;

/**
 * DAO for WorldObjectProperties.
 */

public interface WorldObjectPropertiesDAO {

    /**
     * Loads all World Objects Properties.
     */
    void loadAll() throws DAOException;

    /**
     * Retrieves all loaded manufacturables.
     *
     * @return the complete set of manufacturables
     */
    Map<Integer, Manufacturable> getAllManufacturables() throws DAOException;

    /**
     * Retrieves a World Object Properties by id.
     *
     * @param id id of the world object properties to retrieve
     * @return the requested World Object Properties
     */
    WorldObjectProperties getWorldObjectProperties(int id);

}
