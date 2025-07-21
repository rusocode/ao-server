
package com.ao.service;

import com.ao.data.dao.exception.DAOException;

/**
 * NPC Service interface.
 * @author jsotuyod
 */
public interface NPCService {

	/**
	 * Loads all NPCs.
	 * @throws DAOException
	 */
	void loadNPCs() throws DAOException;
}
