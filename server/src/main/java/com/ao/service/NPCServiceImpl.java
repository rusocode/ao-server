package com.ao.service;

import com.ao.data.dao.NPCCharacterPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;
import com.google.inject.Inject;

/**
 * Default NPC Service implementation.
 */

public class NPCServiceImpl implements NPCService {

    private final NPCCharacterPropertiesDAO npcsDAO;
    private NPCProperties[] npcs;

    @Inject
    public NPCServiceImpl(NPCCharacterPropertiesDAO npcsDAO) {
        this.npcsDAO = npcsDAO;
    }

    @Override
    public void loadNPCs() throws DAOException {
        npcs = npcsDAO.load();
    }

}
