package com.ao.model.worldobject;

/**
 * An object within the world.
 */

public interface Object {

    int getId();

    int getGraphic();

    String getName();

    ObjectType getObjectType();

    /**
     * Checks whether the object is fixed in the map or not.
     *
     * @return whether the object is ixed in the map
     */
    boolean isFixed();

}
