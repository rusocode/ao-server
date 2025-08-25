package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

/**
 * DAO for WorldObjectProperties.
 */

public interface NPCCharacterPropertiesDAO {

    /**
     * Loads all NPCs.
     *
     * @return the array of objects
     */
    NPCProperties[] load() throws DAOException;

}
