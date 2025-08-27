package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;

/**
 * An AbstractResourceSource. Does nothing, just sits around.
 */

public abstract class AbstractResourceSource extends AbstractWorldObject {

    public AbstractResourceSource(ResourceSourceProperties properties) {
        super(properties);
    }

    /**
     * Retrieves the id of the produced resource's world object.
     *
     * @return the id of the produced resource's world object
     */
    public int getResourceWorldObjctId() {
        return ((ResourceSourceProperties) properties).getResourceWorldObjctId();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

}
