package com.ao.model.object;

import com.ao.model.object.properties.ResourceSourceProperties;

/**
 * An AbstractResourceSource. Does nothing, just sits around.
 */

public abstract class AbstractResourceSource extends AbstractObject {

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
