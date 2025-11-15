package com.ao.data.dao;

import com.ao.model.map.Map;

/**
 * DAO for maps.
 */

public interface MapDAO {

    /**
     * Loads all maps.
     *
     * @return the array of maps
     */
    Map[] load();

}
