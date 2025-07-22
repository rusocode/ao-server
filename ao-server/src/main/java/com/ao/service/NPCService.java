package com.ao.service;

import com.ao.data.dao.exception.DAOException;

/**
 * NPC Service interface.
 */

public interface NPCService {

    /**
     * Loads all NPCs.
     */
    void loadNPCs() throws DAOException;

}
