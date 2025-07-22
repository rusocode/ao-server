package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */

public class TeleportProperties extends WorldObjectProperties {

    protected int radius;

    /**
     * Creates a new TeleportProperties instance.
     *
     * @param type    type of the item
     * @param id      id of the item
     * @param name    name of the item
     * @param graphic graphic for the item
     * @param radius  radius of the teleport
     */
    public TeleportProperties(WorldObjectType type, int id, String name, int graphic, int radius) {
        super(type, id, name, graphic);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

}
