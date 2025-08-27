package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WoodProperties;

/**
 * A pile of Wood.
 */

public class Wood extends AbstractItem {

    public Wood(WoodProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Wood((WoodProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Can't be used!
    }

    public WoodType getWoodType() {
        return ((WoodProperties) properties).getWoodType();
    }

}
