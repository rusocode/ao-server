package com.ao.model.character.npc;

import com.ao.model.worldobject.Object;
import com.ao.model.worldobject.factory.ObjectFactoryException;

import java.util.List;

/**
 * An interface to cope with the task of choosing what items to drop, if any.
 */

public interface Drop {

    /**
     * Retrieve the list of objects to be dropped.
     *
     * @return a list with the drops
     */
    List<Object> getDrops() throws ObjectFactoryException;

}
