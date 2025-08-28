package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * An item that is consumed when used.
 */

public abstract class ConsumableItem extends AbstractItem {

    public ConsumableItem(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public void use(Character character) {
        // Discount element being used.
        if (--this.amount == 0) character.getInventory().cleanup(); // Clean the inventory
    }

}
