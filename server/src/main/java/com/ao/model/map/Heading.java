package com.ao.model.map;

/**
 * Defines plausible movement headings.
 */

public enum Heading {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    private static final Heading[] VALUES = Heading.values();

    public static Heading get(byte index) {
        if (index < 0 || index >= VALUES.length) return null;
        return VALUES[index];
    }

}
