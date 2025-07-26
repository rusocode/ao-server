package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.ResourceSourceType;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a ResourceSourceProperties property. Allows a lightweight pattern implementation.
 */

public class ResourceSourceProperties extends WorldObjectProperties {

    protected int resourceWorldObjctId;
    protected ResourceSourceType resourceSourceType;

    /**
     * Creates a new ResourceSourceProperties instance.
     *
     * @param type                 type of the item
     * @param id                   id of the item
     * @param name                 name of the item
     * @param graphic              graphic for the item
     * @param resourceWorldObjctId id of the world object being produced by this resource source
     * @param resourceSourceType   the resource source type
     */
    public ResourceSourceProperties(WorldObjectType type, int id, String name, int graphic, int resourceWorldObjctId, ResourceSourceType resourceSourceType) {
        super(type, id, name, graphic);
        this.resourceWorldObjctId = resourceWorldObjctId;
        this.resourceSourceType = resourceSourceType;
    }

    public int getResourceWorldObjctId() {
        return resourceWorldObjctId;
    }

    public ResourceSourceType getResourceSourceType() {
        return resourceSourceType;
    }

}
