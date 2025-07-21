package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.AmmunitionProperties;

/**
 * An ammunition.
 */
public class Ammunition extends AbstractEquipableItem {

	/**
	 * Creates a new Ammunition instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public Ammunition(AmmunitionProperties properties, int amount) {
		super(properties, amount);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new Ammunition((AmmunitionProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		// Ammunitions can't be used.
	}

	/**
	 * @return the minHit
	 */
	public int getMinHit() {
		return ((AmmunitionProperties) properties).getMinHit();
	}

	/**
	 * @return the maxHit
	 */
	public int getMaxHit() {
		return ((AmmunitionProperties) properties).getMaxHit();
	}

	/**
	 * Retrieves the damage to be applied by the item.
	 * @return The damage to be applied by the item.
	 */
	public int getDamage() {
		int minModifier = ((AmmunitionProperties) properties).getMinHit();
		int maxModifier = ((AmmunitionProperties) properties).getMaxHit();
		
		return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
	}
}
