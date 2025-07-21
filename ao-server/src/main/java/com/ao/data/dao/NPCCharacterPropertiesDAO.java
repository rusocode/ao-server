package com.ao.data.dao;


import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.npc.properties.NPCProperties;

/**
 * DAO for WorldObjectProperties.
 */
public interface NPCCharacterPropertiesDAO {

	/**
	 * Loads all World Objects Properties.
	 * @return The complete list of WorldObjectsProperties.
	 * @throws DAOException
	 */
	NPCProperties[] retrieveAll() throws DAOException;
}
