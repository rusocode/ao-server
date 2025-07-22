package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;

/**
 * A potion to recover mana.
 */

public class ManaPotion extends ConsumableItem {

    /**
     * Creates a new ManaPotion instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public ManaPotion(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new ManaPotion((StatModifyingItemProperties) properties, amount);
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
        character.addToMana((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    /**
     * Retrieves the minimum man restored by the potion.
     *
     * @return the minimum mana restored by the potion
     */
    public int getMinMana() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    /**
     * Retrieves the maximum mana restored by the potion.
     *
     * @return the maximum mana restored by the potion
     */
    public int getMaxMana() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
