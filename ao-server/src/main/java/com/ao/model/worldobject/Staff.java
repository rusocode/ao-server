package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.StaffProperties;

/**
 * A magic staff.
 */

public class Staff extends Weapon {

    /**
     * Creates a new Staff instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public Staff(StaffProperties properties, int amount) {
        super(properties, amount);
    }

    /**
     * Retrieves the staff's magic power.
     *
     * @return the staff's magic power
     */
    public int getMagicPower() {
        return ((StaffProperties) properties).getMagicPower();
    }

    /**
     * The damage bonus for this staff.
     *
     * @return the damage bonus for this staff
     */
    public int getDamageBonus() {
        return ((StaffProperties) properties).getDamageBonus();
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.AbstractItem#clone()
     */
    @Override
    public Item clone() {
        return new Staff((StaffProperties) properties, amount);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.Item#use(ao.model.character.Character)
     */
    @Override
    public void use(Character character) {
        // Staffs aren't used
    }

}
