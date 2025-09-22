package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.StatModifyingItemProperties;

/**
 * Food to be consumed by characters.
 */

public class Food extends ConsumableItem {

    public Food(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Food((StatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
        character.addToHunger((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    public int getMinHun() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    public int getMaxHun() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
