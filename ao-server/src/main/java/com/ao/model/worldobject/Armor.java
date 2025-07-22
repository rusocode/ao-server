package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

/**
 * An armor.
 */

public class Armor extends AbstractDefensiveItem {

    /**
     * Creates a new Armor instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Armor(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Armor((DefensiveItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Armors can't be used, just equipped
    }

}
