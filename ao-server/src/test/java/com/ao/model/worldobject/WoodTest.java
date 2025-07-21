

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WoodProperties;

public class WoodTest extends AbstractItemTest {

	private Wood wood1;
	private Wood wood2;

	@Before
	public void setUp() throws Exception {
		final WoodProperties props1 = new WoodProperties(WorldObjectType.WOOD, 1, "Black Potion", 1, 1, null, null, false, false, false, false, WoodType.NORMAL);
		wood1 = new Wood(props1, 5);

		final WoodProperties props2 = new WoodProperties(WorldObjectType.WOOD, 1, "Black Potion", 1, 1, null, null, false, false, false, false, WoodType.ELVEN);
		wood2 = new Wood(props2, 1);

		object = wood2;
		objectProps = props2;
		ammount = 1;
	}

	@Test
	public void testUse() {
		final Character character = mock(Character.class);

		// nothing should happen
		wood1.use(character);
		wood2.use(character);

		verifyZeroInteractions(character);
	}

	@Test
	public void testClone() {
		final Wood clone = (Wood) wood1.clone();

		// Make sure all fields match
		assertEquals(wood1.amount, clone.amount);
		assertEquals(wood1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(wood1, clone);


		final Wood clone2 = (Wood) wood2.clone();

		// Make sure all fields match
		assertEquals(wood2.amount, clone2.amount);
		assertEquals(wood2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(wood2, clone2);
	}

	@Test
	public void testGetWoodType() {
		assertEquals(WoodType.NORMAL, wood1.getWoodType());
		assertEquals(WoodType.ELVEN, wood2.getWoodType());
	}
}
