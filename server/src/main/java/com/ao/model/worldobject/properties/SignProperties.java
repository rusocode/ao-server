package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */

public class SignProperties extends WorldObjectProperties {

    protected int bigGraphic;
    protected String text;

    /**
     * Creates a new SignProperties instance.
     *
     * @param type       type of the item
     * @param id         id of the item
     * @param name       name of the item
     * @param graphic    graphic for the item
     * @param bigGraphic big graphic for the item
     * @param text       text for the item
     */
    public SignProperties(WorldObjectType type, int id, String name, int graphic, int bigGraphic, String text) {
        super(type, id, name, graphic);
        this.bigGraphic = bigGraphic;
        this.text = text;
    }

    public int getBigGraphic() {
        return bigGraphic;
    }

    public String getText() {
        return text;
    }

}
