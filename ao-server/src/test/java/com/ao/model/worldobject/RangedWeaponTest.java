package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.RangedWeaponProperties;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class RangedWeaponTest extends AbstractEquipableItemTest {

    protected static final int MIN_HIT = 1;
    protected static final int MAX_HIT = 5;
    protected static final int PIERCING_DAMAGE = 4;

    protected RangedWeapon weapon1;
    protected RangedWeapon weapon2;

    @Before
    public void setUp() throws Exception {
        final RangedWeaponProperties props1 = new RangedWeaponProperties(WorldObjectType.WEAPON, 1, "Throw Knifes", 1, 1, 0, null, null, false, false, false, false, 1, true, PIERCING_DAMAGE, MIN_HIT, MAX_HIT, false);
        weapon1 = new RangedWeapon(props1, 5);

        final RangedWeaponProperties props2 = new RangedWeaponProperties(WorldObjectType.WEAPON, 1, "Crossbow", 1, 1, 0, null, null, false, false, false, false, 1, false, PIERCING_DAMAGE, MAX_HIT, MAX_HIT, true);
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
        assertEquals(weapon1.amount, clone.amount);
        assertEquals(weapon1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(weapon1, clone);


        final RangedWeapon clone2 = (RangedWeapon) weapon2.clone();

        // Make sure all fields match
        assertEquals(weapon2.amount, clone2.amount);
        assertEquals(weapon2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(weapon2, clone2);
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
        assertEquals(MIN_HIT, weapon1.getMinHit());
        assertEquals(MAX_HIT, weapon2.getMinHit());
    }

    @Test
    public void testGetMaxHit() {
        assertEquals(MAX_HIT, weapon1.getMaxHit());
        assertEquals(MAX_HIT, weapon2.getMaxHit());
    }

    @Test
    public void testGetPiercingDamage() {
        assertEquals(PIERCING_DAMAGE, weapon1.getPiercingDamage());
        assertEquals(PIERCING_DAMAGE, weapon2.getPiercingDamage());
    }

    @Test
    public void testGetStabs() {
        assertTrue(weapon1.getStabs());
        assertFalse(weapon2.getStabs());
    }

    @Test
    public void testGetDamage() {
        final int damage = weapon1.getDamage();

        assertThat(damage, greaterThanOrEqualTo(MIN_HIT));
        assertThat(damage, lessThanOrEqualTo(MAX_HIT));
        assertEquals(MAX_HIT, weapon2.getDamage());
    }

    @Test
    public void testGetNeedsAmmunition() {
        assertFalse(weapon1.getNeedsAmmunition());
        assertTrue(weapon2.getNeedsAmmunition());
    }

}
