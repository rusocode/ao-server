package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.properties.ObjectProperties;
import com.ao.model.worldobject.properties.crafting.Craftable;

import java.util.Map;

public interface ObjectDAO {

    /**
     * Loads all objects.
     */
    void load() throws DAOException;

    /**
     * Gets all loaded craftables.
     *
     * @return the complete set of craftables
     */
    Map<Integer, Craftable> getAllCraftables() throws DAOException;

    /**
     * Gets a {@code ObjectProperties} by id.
     *
     * @param id id of the object properties to retrieve
     * @return the requested Object Properties
     */
    ObjectProperties getObjectProperties(int id);

}
