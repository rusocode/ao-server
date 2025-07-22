package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WoodProperties;

/**
 * A pile of Wood.
 */

public class Wood extends AbstractItem {

    /**
     * Creates a new Wood instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Wood(WoodProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Wood((WoodProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Can't be used!
    }

    /**
     * Retrieves the type of wood this item represents.
     *
     * @return The type of wood this item represents.
     */
    public WoodType getWoodType() {
        return ((WoodProperties) properties).getWoodType();
    }

}
