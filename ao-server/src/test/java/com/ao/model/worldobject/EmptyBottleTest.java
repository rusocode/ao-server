

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
import com.ao.model.worldobject.properties.RefillableStatModifyingItemProperties;

public class EmptyBottleTest extends AbstractItemTest {

	private EmptyBottle bottle1;
	private EmptyBottle bottle2;

	@Before
	public void setUp() throws Exception {
		final RefillableStatModifyingItemProperties props1 = new RefillableStatModifyingItemProperties(WorldObjectType.EMPTY_BOTTLE, 1, "Empty Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
		bottle1 = new EmptyBottle(props1, 1);

		final RefillableStatModifyingItemProperties props2 = new RefillableStatModifyingItemProperties(WorldObjectType.EMPTY_BOTTLE, 1, "Empty Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
		bottle2 = new EmptyBottle(props2, 1);

		object = bottle2;
		ammount = 1;
		objectProps = props2;
	}

	@Test
	public void testUse() {
		final Inventory inventory = mock(Inventory.class);
		final Character character = mock(Character.class);
		when(character.getInventory()).thenReturn(inventory);

		bottle1.use(character);
		bottle2.use(character);

		// Usage of empty botttles do nothing.
		verifyZeroInteractions(character);
	}

	@Test
	public void testClone() {
		final EmptyBottle clone = (EmptyBottle) bottle1.clone();

		// Make sure all fields match
		assertEquals(bottle1.amount, clone.amount);
		assertEquals(bottle1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(bottle1, clone);


		final EmptyBottle clone2 = (EmptyBottle) bottle2.clone();

		// Make sure all fields match
		assertEquals(bottle2.amount, clone2.amount);
		assertEquals(bottle2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(bottle2, clone2);
	}

}
