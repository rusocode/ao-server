package com.ao.model.map;

public class Position {

    private byte x, y;
    private int map;

    // TODO Deberia cambiar la posicion de parametros, primero map?
    public Position(byte x, byte y, int map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    /**
     * Adds (or substract if the given number is negative) positions to the x axis.
     *
     * @param positions positions to add
     */
    public void addToX(int positions) {
        // TODO Chequear que el numero no se vaya fuera de los rangos?
        x += positions;
    }

    /**
     * Adds (or substract if the given number is negative) positions to the Y axis.
     *
     * @param positions positions to add
     */
    public void addToY(int positions) {
        // TODO Chequear que el numero no se vaya fuera de los rangos?
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
        if (map != pos.map || Math.abs(x - pos.x) > Map.VISIBLE_AREA_WIDTH || Math.abs(y - pos.y) > Map.VISIBLE_AREA_HEIGHT)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + map;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

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

    public byte getX() {
        return x;
    }

    public void setX(byte x) {
        this.x = x;
    }

    public byte getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", map=" + map + "]";
    }

}