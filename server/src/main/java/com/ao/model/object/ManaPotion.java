package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.StatModifyingItemProperties;

public class ManaPotion extends ConsumableItem {

    public ManaPotion(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new ManaPotion((StatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
        character.addToMana((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    public int getMinMana() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    public int getMaxMana() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
