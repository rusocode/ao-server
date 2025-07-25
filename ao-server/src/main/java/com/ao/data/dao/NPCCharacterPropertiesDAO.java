package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

/**
 * DAO for WorldObjectProperties.
 */

public interface NPCCharacterPropertiesDAO {

    /**
     * Loads all World Objects Properties.
     *
     * @return the complete list of WorldObjectsProperties
     */
    NPCProperties[] retrieveAll() throws DAOException;

}
