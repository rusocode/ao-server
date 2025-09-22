package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.BoatProperties;

/**
 * A boat to navigate across the sea.
 */

public class Boat extends AbstractDefensiveItem {

    public Boat(BoatProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Boat((BoatProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // We do nothing to the character
    }

    public int getMinHit() {
        return ((BoatProperties) properties).getMinHit();
    }

    public int getMaxHit() {
        return ((BoatProperties) properties).getMaxHit();
    }


    public int getDamageBonus() {
        int minModifier = ((BoatProperties) properties).getMinHit();
        int maxModifier = ((BoatProperties) properties).getMaxHit();
        return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
    }

    @Override
    public boolean canBeStolen() {
        return false; // Boats can't steal
    }

    /**
     * Retrieves the usage difficulty of the item.
     *
     * @return the usage difficulty of the item
     */
    public int getUsageDifficulty() {
        return ((BoatProperties) properties).getNavigationSkill();
    }

}
