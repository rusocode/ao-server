package com.ao.model.map.area;

import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;

public class AreaInfo {

    /* Area size is 9, since that's the larger number that can cover a 100x100 map using less than 16 bits (a VB6 integer) */
    public static final int AREA_SIZE = 9;

    private static final int[] AREAS_RECEIVE;

    static {
        // This assumes maps will always be square
        AREAS_RECEIVE = new int[WorldMap.MAP_WIDTH / AreaInfo.AREA_SIZE + 1];
        for (int i = 1; i <= AREAS_RECEIVE.length; i++) {
            // Set the bit + the i-1 bit + the i+1 bit (if they exist)
            AREAS_RECEIVE[i] = (1 << i) | (i != 0 ? 1 << (i - 1) : 0) | (i != AREAS_RECEIVE.length ? 1 << (i + 1) : 0);
        }
    }

    private int belongsX, belongsY;
    private int receivesX, receivesY;
    private int minX, minY;
    private int id;

    private AreaInfo(int belongsX, int belongsY, int receivesX, int receivesY, int minX, int minY, int id) {
        super();
        this.belongsX = belongsX;
        this.belongsY = belongsY;
        this.receivesX = receivesX;
        this.receivesY = receivesY;
        this.minX = minX;
        this.minY = minY;
        this.id = id;
    }

    public static int getAreaIdForPos(Position pos) {
        return getAreaIdForPos(pos.getX(), pos.getY());
    }

    public static int getAreaIdForPos(int x, int y) {
        return (x / AREA_SIZE + 1) * (y / AREA_SIZE + 1);
    }

    public void changeCurrentAreTowards(Heading heading) {
        switch (heading) {
            case NORTH:
                minY -= AREA_SIZE;
                break;
            case SOUTH:
                minY += AREA_SIZE;
                break;
            case WEST:
                minX += AREA_SIZE;
                break;
            case EAST:
                minX += AREA_SIZE;
                break;
        }
        // Update area data
        updateAreaData();
    }

    public void setForPosition(Position pos) {
        minX = (pos.getX() / AREA_SIZE - 1) * 9;
        minY = (pos.getY() / AREA_SIZE - 1) * 9;
        // Update area data
        updateAreaData();
    }

    public boolean isInSameArea(Position position) {
        return id == AreaInfo.getAreaIdForPos(position);
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    private void updateAreaData() {
        id = getAreaIdForPos(minX, minY);

        int bitX = minX / AREA_SIZE + 1;
        receivesX = AREAS_RECEIVE[bitX];
        belongsX = 1 << bitX;

        int bitY = minY / AREA_SIZE + 1;
        receivesY = AREAS_RECEIVE[bitY];
        belongsY = 1 << bitY;
    }

}