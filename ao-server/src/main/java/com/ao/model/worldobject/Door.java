package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DoorProperties;
import com.ao.model.worldobject.properties.WorldObjectProperties;

public class Door extends AbstractWorldObject {

    public Door(WorldObjectProperties properties) {
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
