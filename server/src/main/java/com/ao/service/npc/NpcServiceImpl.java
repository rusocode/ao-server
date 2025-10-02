package com.ao.service.npc;

import com.ao.data.dao.NpcCharacterDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.Npc;
import com.ao.service.NpcService;
import com.google.inject.Inject;

/**
 * Default npc service implementation.
 */

public class NpcServiceImpl implements NpcService {

    private final NpcCharacterDAO npcDAO;
    private Npc[] npcs;

    @Inject
    public NpcServiceImpl(NpcCharacterDAO npcDAO) {
        this.npcDAO = npcDAO;
    }

    @Override
    public void loadNpcs() throws DAOException {
        npcs = npcDAO.load();
    }

    @Override
    public Npc getNpc(int id) {
        // Npc enumeration starts at 1, not 0
        return npcs[id - 1];
    }

}
