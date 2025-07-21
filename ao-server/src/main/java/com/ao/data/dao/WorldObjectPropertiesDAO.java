package com.ao.data.dao;


import java.util.Map;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.ao.model.worldobject.properties.manufacture.Manufacturable;

/**
 * DAO for WorldObjectProperties.
 */
public interface WorldObjectPropertiesDAO {

	/**
	 * Loads all World Objects Properties.
	 * @throws DAOException
	 */
	void loadAll() throws DAOException;

	/**
	 * Retrieves all loaded manufacturables.
	 * @return The complete set of manufacturables.
	 * @throws DAOException
	 */
	Map<Integer, Manufacturable> getAllManufacturables() throws DAOException;

	/**
	 * Retrieves a World Object Properties by id.
	 * @param id The id of the world object properties to retrieve.
	 * @return The requested World Object Properties
	 */
	WorldObjectProperties getWorldObjectProperties(int id);
}
