package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.WorldObjectProperties;

/**
 * Abstract implementation of world object, provides most functionality.
 */

public abstract class AbstractWorldObject implements WorldObject {

    protected WorldObjectProperties properties;

    /**
     * Creates a new AbstractWorldObject instance.
     *
     * @param properties the object's properties
     */
    public AbstractWorldObject(WorldObjectProperties properties) {
        this.properties = properties;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.WorldObject#getGraphic()
     */
    @Override
    public int getGraphic() {
        return properties.getGraphic();
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.WorldObject#getId()
     */
    @Override
    public int getId() {
        return properties.getId();
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.WorldObject#getName()
     */
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
