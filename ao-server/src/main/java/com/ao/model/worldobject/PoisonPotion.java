package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * A potion to heal from poison.
 */
public class PoisonPotion extends ConsumableItem {

	/**
	 * Creates a new PoisonPotion instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public PoisonPotion(ItemProperties properties, int amount) {
		super(properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new PoisonPotion((ItemProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		super.use(character);
		
		// Heal poison!
		character.setPoisoned(false);
	}
}
