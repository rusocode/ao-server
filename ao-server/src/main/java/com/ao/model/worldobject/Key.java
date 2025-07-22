package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.KeyProperties;

/**
 * A Key.
 */

public class Key extends AbstractItem {

    /**
     * Creates a new instance of a key.
     *
     * @param properties key's properties
     * @param amount     amount of keys being created
     */
    public Key(KeyProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Key((KeyProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // TODO Auto-generated method stub
    }

    /**
     * Retrieves the code for this key.
     *
     * @return the key's code
     */
    public int getCode() {
        return ((KeyProperties) properties).getCode();
    }

}
