package com.ao.model.worldobject;

/**
 * An object within the world.
 */

public interface WorldObject {

    /**
     * Retrieves the item's unique id.
     *
     * @return the item's unique id
     */
    int getId();

    /**
     * Retrieves the object's graphic index.
     *
     * @return the object's graphic index
     */
    int getGraphic();

    /**
     * Retrieves the object's name.
     *
     * @return the object's name
     */
    String getName();

    /**
     * Retrieves the object's type.
     *
     * @return the object's type
     */
    WorldObjectType getObjectType();

    /**
     * Checks whether the object is fixed in the map or not.
     *
     * @return whether the object is ixed in the map
     */
    boolean isFixed();

}
