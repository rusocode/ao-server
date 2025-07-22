package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.TemporalStatModifyingItemProperties;

/**
 * A potion to increase dexterity.
 */

public class DexterityPotion extends ConsumableItem {

    // TODO Add timing for effect!!

    /**
     * Creates a new DexerityPotion instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public DexterityPotion(TemporalStatModifyingItemProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new DexterityPotion((TemporalStatModifyingItemProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.ConsumableItem#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        super.use(character);
        int minModifier = ((TemporalStatModifyingItemProperties) properties).getMinModifier();
        int maxModifier = ((TemporalStatModifyingItemProperties) properties).getMaxModifier();
        int time = ((TemporalStatModifyingItemProperties) properties).getEffectDuration();
        // increase dexterity!
        character.addToDexterity((int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier, time);
    }

    /**
     * Retrieves the minimum modifier for this potion.
     *
     * @return the minimum modifier for this potion
     */
    public int getMinModifier() {
        return ((TemporalStatModifyingItemProperties) properties).getMinModifier();
    }

    /**
     * Retrieves the maximim modifier for this potion.
     *
     * @return the maximim modifier for this potion
     */
    public int getMaxModifier() {
        return ((TemporalStatModifyingItemProperties) properties).getMaxModifier();
    }

    /**
     * Retrieves the effect's duration.
     *
     * @return the effect's duration
     */
    public int getEffectDuration() {
        return ((TemporalStatModifyingItemProperties) properties).getEffectDuration();
    }

}
