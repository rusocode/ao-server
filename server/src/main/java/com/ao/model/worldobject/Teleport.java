package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.TeleportProperties;

public class Teleport extends AbstractObject {

    public Teleport(TeleportProperties properties) {
        super(properties);
    }

    public int getRadius() {
        return ((TeleportProperties) properties).getRadius();
    }

}
