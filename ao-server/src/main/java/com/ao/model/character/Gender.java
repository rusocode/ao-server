package com.ao.model.character;

/**
 * List of character's genders.
 */
public enum Gender {
	FEMALE,
	MALE;
	
	/**
	 * Enum values.
	 */
	private static Gender[] values = Gender.values();
	
	/**
	 * Retrieves the gender with the given index.
	 * @param index The gender index.
	 * @return The gender.
	 */
	public static Gender get(byte index) {
		return values[index];
	}
}
