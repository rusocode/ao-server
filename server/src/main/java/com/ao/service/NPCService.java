package com.ao.service;

import com.ao.data.dao.exception.DAOException;

/**
 * NPC Service interface.
 */

public interface NPCService {

    void loadNpcs() throws DAOException;

}
