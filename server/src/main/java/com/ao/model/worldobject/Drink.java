package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;

/**
 * Drink to be consumed by characters.
 */

public class Drink extends ConsumableItem {

    /**
     * Creates a new drink instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Drink(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Drink((StatModifyingItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
        character.addToThirstiness((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    /**
     * Retrieves the minimum thirst restored by the drink.
     *
     * @return the minimum thirst restored by the drink
     */
    public int getMinThirst() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    /**
     * Retrieves the maximum thirst restored by the drink.
     *
     * @return the maximum thirst restored by the drink
     */
    public int getMaxThirst() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
