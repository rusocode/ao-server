package com.ao.model.object;

import com.ao.model.object.properties.ObjectProperties;

/**
 * Abstract implementation of an object provides most functionality.
 */

public abstract class AbstractObject implements Object {

    protected ObjectProperties properties;

    public AbstractObject(ObjectProperties properties) {
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
    public ObjectType getObjectType() {
        return properties.getType();
    }

    @Override
    public boolean isFixed() {
        return false;
    }

}
