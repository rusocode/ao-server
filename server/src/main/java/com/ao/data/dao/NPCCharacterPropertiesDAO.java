package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

public interface NPCCharacterPropertiesDAO {

    /**
     * Loads all npcs.
     *
     * @return the array of npcs
     */
    NPCProperties[] load() throws DAOException;

}
