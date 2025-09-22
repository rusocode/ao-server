package com.ao.model.object;

import com.ao.model.object.properties.DoorProperties;
import com.ao.model.object.properties.ObjectProperties;

public class Door extends AbstractObject {

    public Door(ObjectProperties properties) {
        super(properties);
    }

    public boolean getOpen() {
        return ((DoorProperties) properties).getOpen();
    }

    public boolean getLocked() {
        return ((DoorProperties) properties).getLocked();
    }

    public int getCode() {
        return ((DoorProperties) properties).getCode();
    }

}
