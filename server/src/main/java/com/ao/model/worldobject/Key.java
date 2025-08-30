package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.KeyProperties;

public class Key extends AbstractItem {

    public Key(KeyProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Key((KeyProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
    }

    public int getCode() {
        return ((KeyProperties) properties).getCode();
    }

}
