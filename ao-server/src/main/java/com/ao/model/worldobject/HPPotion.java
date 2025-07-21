package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;

/**
 * A potion to recover hit points.
 */
public class HPPotion extends ConsumableItem {
	
	/**
	 * Creates a new HPPotion instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public HPPotion(StatModifyingItemProperties properties, int amount) {
		super(properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new HPPotion((StatModifyingItemProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		super.use(character);
		
		int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
		int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
		
		character.addToHitPoints((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
	}

	/**
	 * Retrieves the minimum hit points restored by the potion.
	 * @return The minimum hit points restored by the potion.
	 */
	public int getMinHP() {
		return ((StatModifyingItemProperties) properties).getMinModifier();
	}

	/**
	 * Retrieves the maximum hit points restored by the potion.
	 * @return The maximum hit points restored by the potion.
	 */
	public int getMaxHP() {
		return ((StatModifyingItemProperties) properties).getMaxModifier();
	}
}
