package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RangedWeaponProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class RangedWeaponTest extends AbstractEquipableItemTest {

    protected static final int MIN_HIT = 1;
    protected static final int MAX_HIT = 5;
    protected static final int PIERCING_DAMAGE = 4;

    protected RangedWeapon weapon1;
    protected RangedWeapon weapon2;

    @BeforeEach
    public void setUp() throws Exception {
        final RangedWeaponProperties props1 = new RangedWeaponProperties(WorldObjectType.MELEE_WEAPON, 1, "Throw Knifes", 1, 1, 0, null, null, false, false, false, false, 1, true, PIERCING_DAMAGE, MIN_HIT, MAX_HIT, false);
        weapon1 = new RangedWeapon(props1, 5);

        final RangedWeaponProperties props2 = new RangedWeaponProperties(WorldObjectType.MELEE_WEAPON, 1, "Crossbow", 1, 1, 0, null, null, false, false, false, false, 1, false, PIERCING_DAMAGE, MAX_HIT, MAX_HIT, true);
        weapon2 = new RangedWeapon(props2, 1);

        object = weapon1;
        ammount = 5;
        objectProps = props1;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        final RangedWeapon clone = (RangedWeapon) weapon1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(weapon1.amount);
        assertThat(clone.properties).isEqualTo(weapon1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(weapon1);

        final RangedWeapon clone2 = (RangedWeapon) weapon2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(weapon2.amount);
        assertThat(clone2.properties).isEqualTo(weapon2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(weapon2);
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        weapon1.use(character);
        weapon2.use(character);

        verifyNoInteractions(character);
    }

    @Test
    public void testGetMinHit() {
        assertThat(weapon1.getMinHit()).isEqualTo(MIN_HIT);
        assertThat(weapon2.getMinHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetMaxHit() {
        assertThat(weapon1.getMaxHit()).isEqualTo(MAX_HIT);
        assertThat(weapon2.getMaxHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetPiercingDamage() {
        assertThat(weapon1.getPiercingDamage()).isEqualTo(PIERCING_DAMAGE);
        assertThat(weapon2.getPiercingDamage()).isEqualTo(PIERCING_DAMAGE);
    }

    @Test
    public void testGetStabs() {
        assertThat(weapon1.getStabs()).isTrue();
        assertThat(weapon2.getStabs()).isFalse();
    }

    @Test
    public void testGetDamage() {
        final int damage = weapon1.getDamage();

        assertThat(damage).isBetween(MIN_HIT, MAX_HIT);
        assertThat(weapon2.getDamage()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetNeedsAmmunition() {
        assertThat(weapon1.getNeedsAmmunition()).isFalse();
        assertThat(weapon2.getNeedsAmmunition()).isTrue();
    }

}
