package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

/**
 * An armor.
 */

public class Armor extends AbstractDefensiveItem {

    public Armor(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Armor((DefensiveItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Armors can't be used, just equipped
    }

}
