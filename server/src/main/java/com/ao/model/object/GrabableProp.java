package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.ItemProperties;

/**
 * A prop that can be picked up or sold but still does nothing.
 */

public class GrabableProp extends AbstractItem {

    public GrabableProp(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new GrabableProp((ItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // We do nothing, it's still just a prop
    }

}
