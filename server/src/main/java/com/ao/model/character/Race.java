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
    private static final Race[] values = Race.values();

    /**
     * Retrieves the race for the given index.
     *
     * @param index race index
     * @return the race
     */
    public static Race get(byte index) {
        return values[index];
    }

}
