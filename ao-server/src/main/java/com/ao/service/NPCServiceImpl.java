
package com.ao.service;

import javax.inject.Inject;

import com.ao.data.dao.NPCCharacterPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

/**
 * Default NPC Service implementation.
 * @author jsotuyod
 */
public class NPCServiceImpl implements NPCService {

	private NPCCharacterPropertiesDAO npcsDAO;
	private NPCProperties[] npcs;

	@Inject
	public NPCServiceImpl(NPCCharacterPropertiesDAO npcsDAO) {
		this.npcsDAO = npcsDAO;
	}

	@Override
	public void loadNPCs() throws DAOException {
		npcs = npcsDAO.retrieveAll();
	}

}
