package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * Raw Mineral's.
 */

public class Mineral extends AbstractItem {

    /**
     * Creates a new Mineral instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Mineral(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Mineral((ItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Can't be used by itself, needs a foundry
    }

}
