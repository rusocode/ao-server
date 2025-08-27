package com.ao.model.map;

/**
 * Defines plausible movement headings.
 */

public enum Heading {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    private static final Heading[] values = Heading.values();

    public static Heading get(byte index) {
        return values[index];
    }

}
