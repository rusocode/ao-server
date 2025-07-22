package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RefillableStatModifyingItemProperties;

/**
 * An empty bottle.
 */

public class EmptyBottle extends AbstractItem {

    /**
     * Creates a new empty bottle instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public EmptyBottle(RefillableStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new EmptyBottle((RefillableStatModifyingItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Can't be used, just refilled
    }

}
