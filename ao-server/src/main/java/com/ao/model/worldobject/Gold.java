package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

public class Gold extends ConsumableItem {

    public Gold(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Gold((ItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        character.addMoney(amount);
        // It's used all at once.
        amount = 0;
        character.getInventory().cleanup();
    }

}
