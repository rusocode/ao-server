package com.ao.model.map;

/**
 * Triggers for map position events.
 */

public enum Trigger {

    NONE,
    UNDER_ROOF,
    trigger_2, // TODO What is this?
    INVALID_POSITION,
    SAFE_ZONE,
    ANTI_PICKET,
    FIGHT_ZONE;

    /**
     * Enum values.
     */
    private static final Trigger[] values = Trigger.values();

    /**
     * Retrieves the trigger with the given index.
     *
     * @param index trigger index
     * @return the gender
     */
    public static Trigger get(short index) {
        return values[index];
    }

}