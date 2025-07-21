package com.ao.model.character.npc.drop;

/**
 * An item that may be dropped.
 * @author jsotuyod
 */
public class Dropable {

	protected int objId;
	protected int amount;
	protected float chance;

	/**
	 * Creates a new Dropable instance.
	 * @param objId The index of the world obejct that may be dropped.
	 * @param amount The amount of the item to be dropped.
	 * @param chance The chance this object has of being dropped.
	 */
	public Dropable(int objId, int amount, float chance) {
		super();
		this.objId = objId;
		this.amount = amount;
		this.chance = chance;
	}

	/**
	 * @return the objId
	 */
	public int getObjId() {
		return objId;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return the chance
	 */
	public float getChance() {
		return chance;
	}
}
