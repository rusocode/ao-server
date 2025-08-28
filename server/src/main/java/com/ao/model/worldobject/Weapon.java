package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WeaponProperties;

public class Weapon extends AbstractEquipableItem {

    public Weapon(WeaponProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public Item clone() {
        return new Weapon((WeaponProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Weapons can't be used
    }

    public boolean getStabs() {
        return ((WeaponProperties) properties).getStabs();
    }

    public int getPiercingDamage() {
        return ((WeaponProperties) properties).getPiercingDamage();
    }

    public int getMinHit() {
        return ((WeaponProperties) properties).getMinHit();
    }

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
