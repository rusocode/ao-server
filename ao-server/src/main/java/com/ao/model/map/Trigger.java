package com.ao.model.map;

/**
 * Triggers for map position events.
 */
public enum Trigger {
	NONE,
    UNDER_ROOF,
    trigger_2, // TODO: What is this?
    INVALID_POSITION,
    SAFE_ZONE,
    ANTI_PICKET,
    FIGHT_ZONE;


	/**
	 * Enum values.
	 */
	private static Trigger[] values = Trigger.values();
	
	/**
	 * Retrieves the trigger with the given index.
	 * @param index The trigger index.
	 * @return The gender.
	 */
	public static Trigger get(short index) {
		return values[index];
	}
	
}