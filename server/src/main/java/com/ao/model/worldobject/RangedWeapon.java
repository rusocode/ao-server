package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RangedWeaponProperties;
import com.ao.model.worldobject.properties.WeaponProperties;

/**
 * A ranged weapon.
 */

public class RangedWeapon extends Weapon {

    /**
     * Creates a new RangedWeapon instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public RangedWeapon(RangedWeaponProperties properties, int amount) {
        super(properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new RangedWeapon((RangedWeaponProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // RangedWeapons can't be used.
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Weapon#getDamage()
     */
    @Override
    public int getDamage() {
        int minModifier = ((WeaponProperties) properties).getMinHit();
        int maxModifier = ((WeaponProperties) properties).getMaxHit();
        return (int) (Math.random() * (maxModifier - minModifier + 1)) + minModifier;
    }

    /**
     * Checks whether the weapon needs or not ammunition.
     *
     * @return true of the weapon requires ammunition, false otherwise
     */
    public boolean getNeedsAmmunition() {
        return ((RangedWeaponProperties) properties).getNeedsAmmunition();
    }

}
