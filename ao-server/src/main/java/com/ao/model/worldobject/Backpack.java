package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.inventory.InventoryImpl;
import com.ao.model.worldobject.properties.BackpackProperties;

/**
 * A backpack.
 */
public class Backpack extends AbstractEquipableItem {

	
	protected Inventory inventory; 
	
	/**
	 * Creates a new backpack instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public Backpack(BackpackProperties properties, int amount) {
		super(properties, amount);
		
		this.inventory = new InventoryImpl(properties.getSlots());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.AbstractItem#clone()
	 */
	@Override
	public Item clone() {
		return new Backpack((BackpackProperties) properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
	 */
	@Override
	public void use(Character character) {
		// Backpacks can't be used.
	}
	
	/**
	 * @return the slots to be added
	 */
	public int getSlots() {
		return ((BackpackProperties) properties).getSlots();
	}

}
