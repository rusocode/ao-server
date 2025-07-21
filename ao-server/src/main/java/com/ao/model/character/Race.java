package com.ao.model.character;

/**
 * Defines available races.
 */
public enum Race {
	DARK_ELF,
	DWARF,
	ELF,
	GNOME,
	HUMAN;
	
	/**
	 * Enum values.
	 */
	private static Race[] values = Race.values();
	
	/**
	 * Retrieves the race for the given index.
	 * @param index The race index.
	 * @return The race.
	 */
	public static Race get(byte index) {
		return values[index];
	}
}
