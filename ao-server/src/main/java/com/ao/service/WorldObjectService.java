package com.ao.service;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;

public interface WorldObjectService {

    /**
     * Loads/Reloads all objects.
     */
    void loadObjects() throws DAOException;

    /**
     * Retrieves a world object with the given id.
     *
     * @param id     id of the object
     * @param amount amount of objects
     * @return a world object
     */
    WorldObject createWorldObject(int id, int amount) throws WorldObjectFactoryException;

}
