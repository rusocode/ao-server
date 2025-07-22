package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.SignProperties;

public class Sign extends AbstractWorldObject {

    /**
     * Creates a new Sign instance.
     *
     * @param properties object's properties
     */
    public Sign(SignProperties properties) {
        super(properties);
    }

    /**
     * Retrieves the sign's big graphic.
     *
     * @return the sign's a big graphic
     */
    public int getBigGraphic() {
        return ((SignProperties) properties).getBigGraphic();
    }

    /**
     * Retrieves the sign's text.
     *
     * @return the sign's text
     */
    public String getText() {
        return ((SignProperties) properties).getText();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

}
