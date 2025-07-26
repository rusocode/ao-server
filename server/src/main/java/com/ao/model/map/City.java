package com.ao.model.map;

/**
 * A city in the game.
 */

public class City {

    private final int map;
    private final byte x;
    private final byte y;

    /**
     * Creates a new city instance.
     *
     * @param map city map
     * @param x   city x co-ordinate for users spawn
     * @param y   city y co-ordinate for users spawn
     */
    public City(int map, byte x, byte y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the city map.
     *
     * @return the city map
     */
    public int getMap() {
        return map;
    }

    /**
     * Retrieves the city x co-ordinate for users' spawn.
     *
     * @return the city x co-ordinate for users spawn
     */
    public byte getX() {
        return x;
    }

    /**
     * Retrieves the city y co-ordinate for users' spawn.
     *
     * @return the city y co-ordinate for users spawn
     */
    public byte getY() {
        return y;
    }

}
