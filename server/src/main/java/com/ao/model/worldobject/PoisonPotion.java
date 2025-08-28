package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

public class PoisonPotion extends ConsumableItem {

    public PoisonPotion(ItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new PoisonPotion((ItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        // Heal poison!
        character.setPoisoned(false);
    }

}
