package com.ao.service;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.object.Object;
import com.ao.model.object.factory.ObjectFactoryException;

public interface ObjectService {

    /**
     * Loads all objects.
     */
    void loadObjects() throws DAOException;

    /**
     * Retrieves a world object with the given id.
     *
     * @param id     id of the object
     * @param amount number of objects
     * @return a world object
     */
    Object createObject(int id, int amount) throws ObjectFactoryException;

}