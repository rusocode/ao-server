package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.Npc;

public interface NpcCharacterDAO {

    /**
     * Loads all npcs.
     *
     * @return the array of npcs
     */
    Npc[] load() throws DAOException;

}
