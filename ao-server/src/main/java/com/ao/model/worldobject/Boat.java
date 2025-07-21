package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.BoatProperties;

/**
 * A boat to navigate accross the sea.
 */
public class Boat extends AbstractDefensiveItem {
	
	/**
	 * Creates a new Boat instance.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param amount The item's amount.
	 * @param tradeable True if it's tradeable, false otherwise.
	 * @param graphic The graphic for the item.
	 * @param value The item's value.
	 * @param usageDifficulty The item's usage difficulty.
	 * @param manufactureDifficulty The item's manufacture difficulty.
	 * @param forbiddenArchetypes List of UserArchetypes not allowed to use this item.
	 * @param newbie Whether the item is newbie or nor.
	 * @param equippedGraphic The index of the graphic when equipped.
	 * @param minDef The minimum defense granted by this boat.
	 * @param maxDef The maximum defense granted by this boat.
	 * @param minMagicDef The minimum magic defense granted by this item.
	 * @param maxMagicDef The maximum magic defense granted by this item.
	 * @param minHit The minimum hit granted by this boat.
	 * @param maxHit The maximum hit granted by this boat.
	 */
	public Boat(BoatProperties properties, int amount) {
		super(properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new Boat((BoatProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		// We do nothing to the character
	}
	
	/**
	 * Retrieves the minimum hit granted.
	 * @return The minimum hit granted.
	 */
	public int getMinHit() {
		return ((BoatProperties) properties).getMinHit();
	}
	
	/**
	 * Retrieves the maximum hit granted.
	 * @return The maximum hit granted.
	 */
	public int getMaxHit() {
		return ((BoatProperties) properties).getMaxHit();
	}
	
	/**
	 * Retrieves the damage bonus to be applied by the boat.
	 * @return The damage bonus to be applied by the boat.
	 */
	public int getDamageBonus() {
		int minModifier = ((BoatProperties) properties).getMinHit();
		int maxModifier = ((BoatProperties) properties).getMaxHit();
		
		return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#canBeStolen()
	 */
	@Override
	public boolean canBeStolen() {
		return false;	// Boats can't be stolen
	}
	
	/**
	 * Retrieves the usage difficulty of the item.
	 * @return The usage difficulty of the item.
	 */
	public int getUsageDifficulty() {
		return ((BoatProperties) properties).getUsageDifficulty();
	}
}
