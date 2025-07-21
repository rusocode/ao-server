

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;

public class ArmorTest extends AbstractDefensiveItemTest {

	private static final int MIN_DEF = 1;
	private static final int MAX_DEF = 5;

	private static final int MIN_MAGIC_DEF = 10;
	private static final int MAX_MAGIC_DEF = 50;

	private Armor armor1;
	private Armor armor2;

	@Before
	public void setUp() throws Exception {
		final DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.ARMOR, 1, "Leather Armor", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
		armor1 = new Armor(props1, 5);

		final DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.ARMOR, 1, "Leather Armor", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
		armor2 = new Armor(props2, 1);

		object = armor1;
		objectProps = props1;
		ammount = 5;
		itemEquipped = false;
	}

	@Test
	public void testClone() {
		final Armor clone = (Armor) armor1.clone();

		// Make sure all fields match
		assertEquals(armor1.amount, clone.amount);
		assertEquals(armor1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(armor1, clone);


		final Armor clone2 = (Armor) armor2.clone();

		// Make sure all fields match
		assertEquals(armor2.amount, clone2.amount);
		assertEquals(armor2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(armor2, clone2);
	}

	@Test
	public void testUse() {
		final Character character = mock(Character.class);

		// nothing should happen
		armor1.use(character);
		armor2.use(character);
	}

}
