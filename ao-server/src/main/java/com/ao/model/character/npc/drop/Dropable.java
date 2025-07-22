package com.ao.model.character.npc.drop;

/**
 * An item that may be dropped.
 */

public class Dropable {

    protected int objId;
    protected int amount;
    protected float chance;

    /**
     * Creates a new Dropable instance.
     *
     * @param objId  index of the world object that may be dropped
     * @param amount amount of the item to be dropped
     * @param chance chance this object has of being dropped
     */
    public Dropable(int objId, int amount, float chance) {
        super();
        this.objId = objId;
        this.amount = amount;
        this.chance = chance;
    }

    public int getObjId() {
        return objId;
    }

    public int getAmount() {
        return amount;
    }

    public float getChance() {
        return chance;
    }

}
