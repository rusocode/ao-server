package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.TeleportProperties;

public class Teleport extends AbstractWorldObject {

    public Teleport(TeleportProperties properties) {
        super(properties);
    }

    public int getRadius() {
        return ((TeleportProperties) properties).getRadius();
    }

}
