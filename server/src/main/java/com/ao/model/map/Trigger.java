package com.ao.model.map;

public enum Trigger {

    NONE,
    UNDER_ROOF,
    trigger_2, // TODO What is this?
    INVALID_POSITION,
    SAFE_ZONE,
    ANTI_PICKET,
    FIGHT_ZONE;

    public static Trigger get(short index) {
        return Trigger.values()[index];
    }

}