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
        if (x < WorldMap.MIN_X || x > WorldMap.MAX_X)
            throw new IllegalArgumentException("x coordinate must be between " + WorldMap.MIN_X + " and " + WorldMap.MAX_X + " (inclusive), got: " + x);
        if (y < WorldMap.MIN_Y || y > WorldMap.MAX_Y)
            throw new IllegalArgumentException("Y coordinate must be between " + WorldMap.MIN_Y + " and " + WorldMap.MAX_Y + " (inclusive), got: " + y);
    }

}
