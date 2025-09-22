package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.StaffProperties;

/**
 * A magic staff.
 */

public class Staff extends Weapon {

    public Staff(StaffProperties properties, int amount) {
        super(properties, amount);
    }

    public int getMagicPower() {
        return ((StaffProperties) properties).getMagicPower();
    }

    public int getDamageBonus() {
        return ((StaffProperties) properties).getDamageBonus();
    }

    @Override
    public Item clone() {
        return new Staff((StaffProperties) properties, amount);
    }

    @Override
    public void use(Character character) {
        // Staffs aren't used
    }

}
