package com.ao.data.dao;

import com.ao.model.map.WorldMap;

public interface WorldMapDAO {

    /**
     * Loads all maps.
     *
     * @return the array of maps
     */
    WorldMap[] load();

}
