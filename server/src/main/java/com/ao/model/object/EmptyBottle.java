package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.RefillableStatModifyingItemProperties;

/**
 * An empty bottle.
 */

public class EmptyBottle extends AbstractItem {

    public EmptyBottle(RefillableStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new EmptyBottle((RefillableStatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Can't be used, just refilled
    }

}
