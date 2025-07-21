

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;

public class MineralTest extends AbstractItemTest {

	private Mineral mineral1;
	private Mineral mineral2;

	@Before
	public void setUp() throws Exception {
		final ItemProperties props1 = new ItemProperties(WorldObjectType.MINERAL, 1, "Gold", 1, 1, null, null, false, false, false, false);
		mineral1 = new Mineral(props1, 1);

		final ItemProperties props2 = new ItemProperties(WorldObjectType.MINERAL, 1, "Cooper", 1, 1, null, null, false, false, false, false);
		mineral2 = new Mineral(props2, 1);

		object = mineral2;
		ammount = 1;
		objectProps = props2;
	}

	@Test
	public void testUse() {
		final Inventory inventory = mock(Inventory.class);
		final Character character = mock(Character.class);
		when(character.getInventory()).thenReturn(inventory);

		mineral1.use(character);
		mineral2.use(character);

		// Usage of minerals do nothing.
		verifyZeroInteractions(character);
	}

	@Test
	public void testClone() {
		final Mineral clone = (Mineral) mineral1.clone();

		// Make sure all fields match
		assertEquals(mineral1.amount, clone.amount);
		assertEquals(mineral1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(mineral1, clone);


		final Mineral clone2 = (Mineral) mineral2.clone();

		// Make sure all fields match
		assertEquals(mineral2.amount, clone2.amount);
		assertEquals(mineral2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(mineral2, clone2);
	}

}
