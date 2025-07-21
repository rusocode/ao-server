

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.worldobject.properties.StaffProperties;

public class StaffTest extends WeaponTest {

	private static final int MIN_HIT = 1;
	private static final int MAX_HIT = 5;
	private static final int PIERCING_DAMAGE = 4;
	private static final int MAGIC_POWER = 40;
	private static final int DAMAGE_BONUS = 20;

	@Override
	@Before
	public void setUp() throws Exception {
		final StaffProperties props1 = new StaffProperties(WorldObjectType.WEAPON, 1, "Walnut Rod", 1, 1, 0, null, null, false, false, false, false, 1, true, PIERCING_DAMAGE, MIN_HIT, MAX_HIT, MAGIC_POWER, DAMAGE_BONUS);
		weapon1 = new Staff(props1, 5);

		final StaffProperties props2 = new StaffProperties(WorldObjectType.WEAPON, 1, "Plum Rod", 1, 1, 0, null, null, false, false, false, false, 1, false, PIERCING_DAMAGE, MAX_HIT, MAX_HIT, MAGIC_POWER, DAMAGE_BONUS);
		weapon2 = new Staff(props2, 1);

		object = weapon1;
		ammount = 5;
		objectProps = props1;
		itemEquipped = false;
	}

	@Test
	public void testGetDamageBonus() {
		assertEquals(DAMAGE_BONUS, ((Staff) weapon1).getDamageBonus());
		assertEquals(DAMAGE_BONUS, ((Staff) weapon2).getDamageBonus());
	}

	@Test
	public void testGetMagicPower() {
		assertEquals(MAGIC_POWER, ((Staff) weapon1).getMagicPower());
		assertEquals(MAGIC_POWER, ((Staff) weapon2).getMagicPower());
	}
}
