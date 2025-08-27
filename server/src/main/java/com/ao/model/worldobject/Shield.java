package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

public class Shield extends AbstractDefensiveItem {

    public Shield(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Shield((DefensiveItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Shields can't be used, just equipped
    }

}
