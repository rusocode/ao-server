package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RefillableStatModifyingItemProperties;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;

/**
 * A bottle filled with water to be consumed by characters.
 */

public class FilledBottle extends ConsumableItem {

    /**
     * Creates a new filled bottle instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public FilledBottle(RefillableStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new FilledBottle((RefillableStatModifyingItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        super.use(character);
        RefillableStatModifyingItemProperties itemProperties = (RefillableStatModifyingItemProperties) properties;
        character.addToThirstiness(itemProperties.getMaxModifier());
        EmptyBottle emptyBottle = new EmptyBottle(itemProperties.getOtherStateProperties(), 1);
        character.getInventory().addItem(emptyBottle);
    }

    /**
     * Retrieves the thirst restored by the drink.
     *
     * @return the thirst restored by the drink
     */
    public int getThirst() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
