package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * A prop that can be picked up or sold, but still does nothing.
 */
public class GrabableProp extends AbstractItem {

	/**
	 * Creates a new GrabableProp instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public GrabableProp(ItemProperties properties, int amount) {
		super(properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new GrabableProp((ItemProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		// We do nothing, it's still just a prop
	}
}
