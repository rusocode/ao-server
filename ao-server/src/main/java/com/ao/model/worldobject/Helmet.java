package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

/**
 * A helmet.
 */

public class Helmet extends AbstractDefensiveItem {

    /**
     * Creates a new Helmet instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Helmet(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Helmet((DefensiveItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Helmets can't be used, just equipped
    }

}
