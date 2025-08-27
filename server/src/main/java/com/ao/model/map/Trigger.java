package com.ao.model.map;

public enum Trigger {

    NONE,
    UNDER_ROOF,
    trigger_2, // TODO What is this?
    INVALID_POSITION,
    SAFE_ZONE,
    ANTI_PICKET,
    FIGHT_ZONE;

    private static final Trigger[] values = Trigger.values();

    public static Trigger get(short index) {
        return values[index];
    }

}