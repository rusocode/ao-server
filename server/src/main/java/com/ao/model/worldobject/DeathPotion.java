package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * A potion to kill self.
 */

public class DeathPotion extends ConsumableItem {

    /**
     * Creates a new DeathPotion instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public DeathPotion(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new DeathPotion((ItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        super.use(character);
        // Remove all his HP points!
        character.addToHitPoints(-character.getHitPoints());
    }

}
