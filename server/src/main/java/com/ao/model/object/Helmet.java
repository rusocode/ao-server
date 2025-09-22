package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.DefensiveItemProperties;

public class Helmet extends AbstractDefensiveItem {

    public Helmet(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Helmet((DefensiveItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Helmets can't be used, just equipped
    }

}
