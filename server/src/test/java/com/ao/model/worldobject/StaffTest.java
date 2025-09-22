package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.StaffProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StaffTest extends WeaponTest {

    private static final int MIN_HIT = 1;
    private static final int MAX_HIT = 5;
    private static final int PIERCING_DAMAGE = 4;
    private static final int MAGIC_POWER = 40;
    private static final int DAMAGE_BONUS = 20;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        StaffProperties props1 = new StaffProperties(ObjectType.MELEE_WEAPON, 1, "Walnut Rod", 1, 1, 0, null, null, false, false, false, false, 1, true, PIERCING_DAMAGE, MIN_HIT, MAX_HIT, MAGIC_POWER, DAMAGE_BONUS);
        weapon1 = new Staff(props1, 5);

        StaffProperties props2 = new StaffProperties(ObjectType.MELEE_WEAPON, 1, "Plum Rod", 1, 1, 0, null, null, false, false, false, false, 1, false, PIERCING_DAMAGE, MAX_HIT, MAX_HIT, MAGIC_POWER, DAMAGE_BONUS);
        weapon2 = new Staff(props2, 1);

        object = weapon1;
        ammount = 5;
        objectProps = props1;
        itemEquipped = false;
    }

    @Test
    public void testGetDamageBonus() {
        assertThat(((Staff) weapon1).getDamageBonus()).isEqualTo(DAMAGE_BONUS);
        assertThat(((Staff) weapon2).getDamageBonus()).isEqualTo(DAMAGE_BONUS);
    }

    @Test
    public void testGetMagicPower() {
        assertThat(((Staff) weapon1).getMagicPower()).isEqualTo(MAGIC_POWER);
        assertThat(((Staff) weapon2).getMagicPower()).isEqualTo(MAGIC_POWER);
    }

}
