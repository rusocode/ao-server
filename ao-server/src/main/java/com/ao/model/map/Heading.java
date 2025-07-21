package com.ao.model.map;


/**
 * Defines plausible movement headings.
 */
public enum Heading {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	/**
	 * Enum values.
	 */
	private static Heading[] values = Heading.values();
	
	/**
	 * Retrieves the heading for the given index.
	 * @param index The heading index.
	 * @return The heading.
	 */
	public static Heading get(byte index) {
		return values[index];
	}
}
