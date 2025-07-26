package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a door's properties. Allows a lightweight pattern implementation.
 */

public class DoorProperties extends WorldObjectProperties {

    protected boolean open;
    protected boolean locked;
    protected int code;
    protected DoorProperties otherStateProperties;

    /**
     * Creates a new DoorProperties instance.
     *
     * @param type                 type of the item
     * @param id                   id of the item
     * @param name                 name of the item
     * @param graphic              graphic for the item
     * @param open                 whether this door is open
     * @param locked               whether this door is locked
     * @param code                 code used to unlock this door
     * @param otherStateProperties object properties for the other state
     */
    public DoorProperties(WorldObjectType type, int id, String name, int graphic,
                          boolean open, boolean locked, int code, DoorProperties otherStateProperties) {
        super(type, id, name, graphic);
        this.open = open;
        this.locked = locked;
        this.code = code;
        if (otherStateProperties != null) setOtherStateProperties(otherStateProperties);
    }

    public boolean getOpen() {
        return open;
    }

    public boolean getLocked() {
        return locked;
    }

    public int getCode() {
        return code;
    }

    public DoorProperties getOtherStateProperties() {
        return otherStateProperties;
    }

    /**
     * Links this object with the one for the other state (open or closed).
     *
     * @param props properties of the object to which to link it
     */
    private void setOtherStateProperties(DoorProperties props) {
        otherStateProperties = props;
        props.otherStateProperties = this;
    }

}