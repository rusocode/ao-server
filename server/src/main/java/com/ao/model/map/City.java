package com.ao.model.map;

/**
 * A city in the game.
 */

public record City(int map, byte x, byte y) {

    /**
     * Creates a new city instance.
     *
     * @param map city map
     * @param x   city x coordinate
     * @param y   city y coordinate
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public City {
        if (map < 0) throw new IllegalArgumentException("Map must be non-negative, got: " + map);
        if (x < Map.MIN_X || x > Map.MAX_X)
            throw new IllegalArgumentException("x coordinate must be between " + Map.MIN_X + " and " + Map.MAX_X + " (inclusive), got: " + x);
        if (y < Map.MIN_Y || y > Map.MAX_Y)
            throw new IllegalArgumentException("Y coordinate must be between " + Map.MIN_Y + " and " + Map.MAX_Y + " (inclusive), got: " + y);
    }

}
