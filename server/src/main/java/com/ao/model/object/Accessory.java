package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.DefensiveItemProperties;

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
