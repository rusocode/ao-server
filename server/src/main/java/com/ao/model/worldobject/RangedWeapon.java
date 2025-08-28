package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RangedWeaponProperties;
import com.ao.model.worldobject.properties.WeaponProperties;

public class RangedWeapon extends Weapon {

    public RangedWeapon(RangedWeaponProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new RangedWeapon((RangedWeaponProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // RangedWeapons can't be used
    }

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
