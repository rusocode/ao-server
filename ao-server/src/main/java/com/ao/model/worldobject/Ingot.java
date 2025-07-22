package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

/**
 * An Ingot.
 */

public class Ingot extends AbstractItem {

    /**
     * Creates a new Ingot instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Ingot(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Ingot((ItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
    }

}
