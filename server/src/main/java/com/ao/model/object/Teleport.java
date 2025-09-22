package com.ao.model.object;

import com.ao.model.object.properties.TeleportProperties;

public class Teleport extends AbstractObject {

    public Teleport(TeleportProperties properties) {
        super(properties);
    }

    public int getRadius() {
        return ((TeleportProperties) properties).getRadius();
    }

}
