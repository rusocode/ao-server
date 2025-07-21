package com.ao.model.character;

/**
 * List of available NPC Types.
 */
public enum NPCType {
	MERCHANT,
	COMMON,
	RESUCITATOR,
	ROYAL_GUARD,
	TRAINER,
	BANKER,
	NOBLE,
	GAMBLER,
	CHAOS_GUARD,
	NEWBIE_RESUCITATOR,
	GOVERNOR,
	DRAGON;

	/**
	 * Enum values.
	 */
	private static NPCType[] values = NPCType.values();

	/**
	 * Retrieves the npc's type for the given index.
	 * @param index The npc's type index.
	 * @return The npc's type .
	 */
	public static NPCType get(byte index) {
		return values[index];
	}
}
