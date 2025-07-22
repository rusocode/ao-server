package com.ao.data.dao;

import com.ao.model.map.WorldMap;

public interface WorldMapDAO {

    /**
     * Loads and retrieves all maps.
     *
     * @return the loaded maps
     */
    WorldMap[] retrieveAll();

}
