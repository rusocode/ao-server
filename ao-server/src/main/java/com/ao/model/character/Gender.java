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
    private static final Gender[] values = Gender.values();

    /**
     * Retrieves the gender with the given index.
     *
     * @param index gender index
     * @return the gender
     */
    public static Gender get(byte index) {
        return values[index];
    }

}
