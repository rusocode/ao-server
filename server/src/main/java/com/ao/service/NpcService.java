package com.ao.service;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.Npc;

/**
 * Npc Service interface.
 */

public interface NpcService {

    void loadNpcs() throws DAOException;

    Npc getNpc(int id); // TODO Donde se usa esto?

}
