package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.StatModifyingItemProperties;

/**
 * Drink to be consumed by characters.
 */

public class Drink extends ConsumableItem {

    public Drink(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Drink((StatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
        character.addToThirstiness((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    public int getMinThirst() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    public int getMaxThirst() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
