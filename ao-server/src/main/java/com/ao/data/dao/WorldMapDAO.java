package com.ao.data.dao;

import com.ao.model.map.WorldMap;

public interface WorldMapDAO {

	/**
	 * Loads and retrieves all maps.
	 * @return The loaded maps.
	 */
	WorldMap[] retrieveAll();
}
