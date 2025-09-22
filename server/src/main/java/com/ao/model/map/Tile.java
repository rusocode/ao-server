package com.ao.model.map;

import com.ao.model.character.Character;
import com.ao.model.object.Object;

public class Tile {

    private final Trigger trigger;
    private final boolean blocked;
    private final boolean isWater;
    private final boolean isLava;
    private final Position tileExit;
    private Character character;
    private Object object;

    /**
     * Creates a new tile.
     *
     * @param blocked   whether the tile is blocked or not
     * @param isWater   whether the tile is water or not
     * @param isLava    whether the tile is lava or not
     * @param trigger   trigger ruling over this tile
     * @param tileExit  Position to which this tile leads, if any
     * @param character character currently standing in this position, if any
     * @param object    object laying in this position, if any
     */
    public Tile(boolean blocked, boolean isWater, boolean isLava, Trigger trigger, Position tileExit, Character character, Object object) {
        this.blocked = blocked;
        this.isWater = isWater;
        this.isLava = isLava;
        this.trigger = trigger;
        this.tileExit = tileExit;
        this.object = object;
        this.character = character;
    }

    public Position getTileExit() {
        return tileExit;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Character getCharacter() {
        return character;
    }

    void setCharacter(final Character character) {
        this.character = character;
    }

    public Object getObject() {
        return object;
    }

    void setObject(Object object) {
        this.object = object;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public boolean isWater() {
        return isWater;
    }

    public boolean isLava() {
        return isLava;
    }

    public boolean isUnderRoof() {
        return trigger == Trigger.UNDER_ROOF;
    }

    public boolean isSafeZone() {
        return trigger == Trigger.SAFE_ZONE;
    }

    @Override
    public String toString() {
        return "Tile [character=" + character + ", object=" + object + ", trigger=" + trigger + ", blocked=" + blocked
                + ", isWater=" + isWater + ", isLava=" + isLava + ", tileExit=" + tileExit + "]";
    }

}
