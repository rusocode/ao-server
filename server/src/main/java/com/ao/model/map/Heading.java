package com.ao.model.map;

/**
 * Defines plausible movement headings.
 */

public enum Heading {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Heading get(byte index) {
        Heading[] values = Heading.values();
        if (index < 0 || index >= values.length) return null;
        return values[index];
    }

}
