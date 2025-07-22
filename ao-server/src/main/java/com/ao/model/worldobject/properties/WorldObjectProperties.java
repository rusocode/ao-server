package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a WorldObject's properties. Allows a lightweight pattern implementation.
 */

public class WorldObjectProperties {

    protected int id;
    protected String name;
    protected int graphic;
    protected WorldObjectType type;

    /**
     * Creates a new WorldObjectProperties instance.
     *
     * @param type    type of the item
     * @param id      id of the item
     * @param name    name of the item
     * @param graphic graphic for the item
     */
    public WorldObjectProperties(WorldObjectType type, int id, String name, int graphic) {
        this.id = id;
        this.name = name;
        this.graphic = graphic;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGraphic() {
        return graphic;
    }

    public WorldObjectType getType() {
        return type;
    }

}
