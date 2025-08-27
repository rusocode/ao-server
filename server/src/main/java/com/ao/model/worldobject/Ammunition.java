package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.AmmunitionProperties;

/**
 * An ammunition.
 */

public class Ammunition extends AbstractEquipableItem {

    public Ammunition(AmmunitionProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Ammunition((AmmunitionProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Ammunitions can't be used
    }

    public int getMinHit() {
        return ((AmmunitionProperties) properties).getMinHit();
    }

    public int getMaxHit() {
        return ((AmmunitionProperties) properties).getMaxHit();
    }

    /**
     * Retrieves the damage to be applied by the item.
     *
     * @return the damage to be applied by the item
     */
    public int getDamage() {
        int minModifier = ((AmmunitionProperties) properties).getMinHit();
        int maxModifier = ((AmmunitionProperties) properties).getMaxHit();
        return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
    }

}
