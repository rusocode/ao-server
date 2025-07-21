

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WeaponProperties;

public class WeaponTest extends AbstractEquipableItemTest {

	protected static final int MIN_HIT = 1;
	protected static final int MAX_HIT = 5;
	protected static final int PIERCING_DAMAGE = 4;

	protected Weapon weapon1;
	protected Weapon weapon2;

	@Before
	public void setUp() throws Exception {
		final WeaponProperties props1 = new WeaponProperties(WorldObjectType.WEAPON, 1, "Bastard Sword", 1, 1, 0, null, null, false, false, false, false, 1, true, PIERCING_DAMAGE, MIN_HIT, MAX_HIT);
		weapon1 = new Weapon(props1, 5);

		final WeaponProperties props2 = new WeaponProperties(WorldObjectType.WEAPON, 1, "Halberd", 1, 1, 0, null, null, false, false, false, false, 1, false, PIERCING_DAMAGE, MAX_HIT, MAX_HIT);
		weapon2 = new Weapon(props2, 1);

		object = weapon1;
		ammount = 5;
		objectProps = props1;
		itemEquipped = false;
	}

	@Test
	public void testClone() {
		final Weapon clone = (Weapon) weapon1.clone();

		// Make sure all fields match
		assertEquals(weapon1.amount, clone.amount);
		assertEquals(weapon1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(weapon1, clone);


		final Weapon clone2 = (Weapon) weapon2.clone();

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

		verifyZeroInteractions(character);
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

		assertTrue(damage >= MIN_HIT);
		assertTrue(damage <= MAX_HIT);
		assertEquals(MAX_HIT, weapon2.getDamage());
	}
}
