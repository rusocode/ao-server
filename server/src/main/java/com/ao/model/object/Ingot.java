package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.ItemProperties;

public class Ingot extends AbstractItem {

    public Ingot(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Ingot((ItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
    }

}
