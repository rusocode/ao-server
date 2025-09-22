package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.ItemProperties;


public class Mineral extends AbstractItem {

    public Mineral(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Mineral((ItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Can't be used by itself, needs a foundry
    }

}
