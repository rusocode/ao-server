package com.ao.service;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

/**
 * NPC Service interface.
 */

public interface NPCService {

    void loadNpcs() throws DAOException;

    NPCProperties getNPCProperties(int id); // TODO Donde se usa esto?

}
