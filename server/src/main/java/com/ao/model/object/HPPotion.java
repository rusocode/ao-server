package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.StatModifyingItemProperties;

public class HPPotion extends ConsumableItem {

    public HPPotion(StatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new HPPotion((StatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((StatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((StatModifyingItemProperties) properties).getMaxModifier();
        character.addToHitPoints((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier);
    }

    public int getMinHP() {
        return ((StatModifyingItemProperties) properties).getMinModifier();
    }

    public int getMaxHP() {
        return ((StatModifyingItemProperties) properties).getMaxModifier();
    }

}
