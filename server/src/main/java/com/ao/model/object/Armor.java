package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.DefensiveItemProperties;

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
