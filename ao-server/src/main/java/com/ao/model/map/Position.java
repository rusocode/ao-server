package com.ao.model.map;

public class Position {

    /**
     * The position in the X axis.
     */
    private byte x;

    /**
     * The position in the Y axis.
     */
    private byte y;

    /**
     * The position's map.
     */
    private int map;

    /**
     * Creates a new position with the given data
     *
     * @param x   position in the X axis
     * @param y   position in the Y axis
     * @param map position's map
     */
    public Position(byte x, byte y, int map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    /**
     * Retrieves the position in the X axis.
     *
     * @return the position in the X axis
     */
    public byte getX() {
        return x;
    }

    /**
     * Sets the position in the X axis.
     *
     * @param x new position in the X axis
     */
    public void setX(byte x) {
        this.x = x;
    }

    /**
     * Retrieves the position in the Y axis.
     *
     * @return the position in the Y axis
     */
    public byte getY() {
        return y;
    }

    /**
     * Sets the position in the Y axis.
     *
     * @param y new position in the Y axis
     */
    public void setY(byte y) {
        this.y = y;
    }

    /**
     * Retrieves the position's map.
     *
     * @return the position's map
     */
    public int getMap() {
        return map;
    }

    /**
     * Sets the position's map.
     *
     * @param map new position's map
     */
    public void setMap(int map) {
        this.map = map;
    }

    /**
     * Adds (or substract if the given number is negative) positions to the X axis.
     *
     * @param positions positions to add
     */
    public void addToX(int positions) {
        // TODO Chequear que el número no se vaya fuera de los rangos?
        x += positions;
    }

    /**
     * Adds (or substract if the given number is negative) positions to the Y axis.
     *
     * @param positions positions to add
     */
    public void addToY(int positions) {
        // TODO Chequear que el número no se vaya fuera de los rangos?
        y += positions;
    }

    /**
     * Calculates the Manhattan distance to the given Position. Assumes both positions are on the same map.
     *
     * @param pos another position to calculate the distance
     * @return the distance to the other position
     */
    public int getDistance(Position pos) {
        return Math.abs(x - pos.x) + Math.abs(y - pos.y);
    }

    /**
     * Checks if the given position is in our vision range.
     *
     * @param pos position to check
     * @return true if the given position is in the vision range, false otherwise
     */
    public boolean inVisionRange(Position pos) {
        if (map != pos.map ||
                Math.abs(x - pos.x) > WorldMap.VISIBLE_AREA_WIDTH ||
                Math.abs(y - pos.y) > WorldMap.VISIBLE_AREA_HEIGHT) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + map;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        if (map != other.map) return false;
        if (x != other.x) return false;
        if (y != other.y) return false;
        return true;
    }

}