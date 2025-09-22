package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.ItemProperties;

/**
 * A potion to kill self.
 */

public class DeathPotion extends ConsumableItem {

    public DeathPotion(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new DeathPotion((ItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        // Remove all his HP points!
        character.addToHitPoints(-character.getHitPoints());
    }

}
