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
        return Heading.values()[index];
    }

}
