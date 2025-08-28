package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.WorldObjectProperties;

/**
 * Abstract implementation of world object, provides most functionality.
 */

public abstract class AbstractWorldObject implements WorldObject {

    protected WorldObjectProperties properties;

    public AbstractWorldObject(WorldObjectProperties properties) {
        this.properties = properties;
    }

    @Override
    public int getGraphic() {
        return properties.getGraphic();
    }

    @Override
    public int getId() {
        return properties.getId();
    }

    @Override
    public String getName() {
        return properties.getName();
    }

    @Override
    public WorldObjectType getObjectType() {
        return properties.getType();
    }

    @Override
    public boolean isFixed() {
        return false;
    }

}
