package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

/**
 * Accessory items such as rings.
 */

public class Accessory extends AbstractDefensiveItem {

    public Accessory(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Accessory((DefensiveItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Accessories are not used, just equipped
    }

}
