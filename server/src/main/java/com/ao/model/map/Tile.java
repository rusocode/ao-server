package com.ao.model.map;

import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

public class Tile {

    private final Trigger trigger;
    private final boolean blocked;
    private final boolean isWater;
    private final boolean isLava;
    private final Position tileExit;
    private Character character;
    private WorldObject worldObject;

    /**
     * Creates a new tile.
     *
     * @param blocked     whether the tile is blocked or not
     * @param isWater     whether the tile is water or not
     * @param isLava      whether the tile is lava or not
     * @param trigger     trigger ruling over this tile
     * @param tileExit    Position to which this tile leads, if any
     * @param character   character currently standing in this position, if any
     * @param worldObject object laying in this position, if any
     */
    public Tile(boolean blocked, boolean isWater, boolean isLava, Trigger trigger, Position tileExit, Character character, WorldObject worldObject) {
        this.blocked = blocked;
        this.isWater = isWater;
        this.isLava = isLava;
        this.trigger = trigger;
        this.tileExit = tileExit;
        this.worldObject = worldObject;
        this.character = character;
    }

    /**
     * Retrieves the position to which this tile leads.
     *
     * @return the tile exit
     */
    public Position getTileExit() {
        return tileExit;
    }

    /**
     * Retrieves the block status.
     *
     * @return true if the tile is blocked, false otherwise
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Retrieves the character at the tile.
     *
     * @return the character at the tile
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Sets the character at the tile.
     *
     * @param character character to be set at the tile
     */
    void setCharacter(final Character character) {
        this.character = character;
    }

    /**
     * Retrieves the world object at the tile.
     *
     * @return the world object at the tile
     */
    public WorldObject getWorldObject() {
        return worldObject;
    }

    /**
     * Sets the world object at the tile.
     *
     * @param worldObject worldObject to set at the tile
     */
    void setWorldObject(final WorldObject worldObject) {
        this.worldObject = worldObject;
    }

    /**
     * Retrieves the trigger at the tile.
     *
     * @return the trigger at the tile
     */
    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Checks if the tile is water.
     *
     * @return true if this tile is water, false otherwise
     */
    public boolean isWater() {
        return isWater;
    }

    /**
     * Checks if the tile is lava.
     *
     * @return true if this tile is lava, false otherwise
     */
    public boolean isLava() {
        return isLava;
    }

    /**
     * Checks if the tile is under a roof.
     *
     * @return true if this tile is under a roof, false otherwise
     */
    public boolean isUnderRoof() {
        return trigger == Trigger.UNDER_ROOF;
    }

    /**
     * Checks if the tile is a safe zone.
     *
     * @return true if this tile is a safe zone, false otherwise
     */
    public boolean isSafeZone() {
        return trigger == Trigger.SAFE_ZONE;
    }

    @Override
    public String toString() {
        return "Tile [character=" + character + ", worldObject=" + worldObject
                + ", trigger=" + trigger + ", blocked=" + blocked
                + ", isWater=" + isWater + ", isLava=" + isLava + ", tileExit="
                + tileExit + "]";
    }

}
