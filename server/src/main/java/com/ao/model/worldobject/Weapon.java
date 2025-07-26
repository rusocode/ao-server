package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WeaponProperties;

/**
 * A weapon.
 */

public class Weapon extends AbstractEquipableItem {

    /**
     * Creates a new Weapon instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Weapon(WeaponProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Weapon((WeaponProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Weapons can't be used.
    }

    /**
     * @return the true if this item stabs, false otherwise
     */
    public boolean getStabs() {
        return ((WeaponProperties) properties).getStabs();
    }

    /**
     * @return the piercingDamage
     */
    public int getPiercingDamage() {
        return ((WeaponProperties) properties).getPiercingDamage();
    }

    /**
     * @return the minHit
     */
    public int getMinHit() {
        return ((WeaponProperties) properties).getMinHit();
    }

    /**
     * @return the maxHit
     */
    public int getMaxHit() {
        return ((WeaponProperties) properties).getMaxHit();
    }

    /**
     * Retrieves the damage to be applied by the item.
     *
     * @return the damage to be applied by the item
     */
    public int getDamage() {
        int minModifier = ((WeaponProperties) properties).getMinHit();
        int maxModifier = ((WeaponProperties) properties).getMaxHit();
        return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
    }

}
