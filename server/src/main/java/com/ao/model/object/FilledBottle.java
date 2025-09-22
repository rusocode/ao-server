package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.RefillableStatModifyingItemProperties;
import com.ao.model.object.properties.StatModifyingItemProperties;

/**
 * A bottle filled with water to be consumed by characters.
 */

public class FilledBottle extends ConsumableItem {

    public FilledBottle(RefillableStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new FilledBottle((RefillableStatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        RefillableStatModifyingItemProperties itemProperties = (RefillableStatModifyingItemProperties) properties;
        character.addToThirstiness(itemProperties.getMaxModifier());
        EmptyBottle emptyBottle = new EmptyBottle(itemProperties.getOtherStateProperties(), 1);
        character.getInventory().addItem(emptyBottle);
    }

    public int getThirst() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
