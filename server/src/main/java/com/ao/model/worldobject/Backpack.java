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

    public Backpack(BackpackProperties properties, int amount) {
        super(properties, amount);
        this.inventory = new InventoryImpl(properties.getSlots());
    }

    @Override
    public Item clone() {
        return new Backpack((BackpackProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Backpacks can't be used
    }

    /**
     * @return the slots to be added
     */
    public int getSlots() {
        return ((BackpackProperties) properties).getSlots();
    }

}
