package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.TemporalStatModifyingItemProperties;

/**
 * A potion to increase dexterity.
 */

public class DexterityPotion extends ConsumableItem {

    // TODO Add timing for effect!

    public DexterityPotion(TemporalStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new DexterityPotion((TemporalStatModifyingItemProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((TemporalStatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((TemporalStatModifyingItemProperties) properties).getMaxModifier();
        int time = ((TemporalStatModifyingItemProperties) properties).getEffectDuration();
        // Increase dexterity!
        character.addToDexterity((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier, time);
    }

    public int getMinModifier() {
        return ((TemporalStatModifyingItemProperties) properties).getMinModifier();
    }

    public int getMaxModifier() {
        return ((TemporalStatModifyingItemProperties) properties).getMaxModifier();
    }

    public int getEffectDuration() {
        return ((TemporalStatModifyingItemProperties) properties).getEffectDuration();
    }

}
