

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;

public class PoisonPotionTest extends AbstractItemTest {

	private PoisonPotion potion1;
	private PoisonPotion potion2;

	@Before
	public void setUp() throws Exception {
		final ItemProperties props1 = new ItemProperties(WorldObjectType.POISON_POTION, 1, "Violet Potion", 1, 1, null, null, false, false, false, false);
		potion1 = new PoisonPotion(props1, 5);

		final ItemProperties props2 = new ItemProperties(WorldObjectType.POISON_POTION, 1, "Violet Potion", 1, 1, null, null, false, false, false, false);
		potion2 = new PoisonPotion(props2, 1);

		object = potion2;
		objectProps = props2;
		ammount = 1;
	}

	@Test
	public void testUseWithCleanup() {
		final Inventory inventory = mock(Inventory.class);
		final Character character = mock(Character.class);
		when(character.getInventory()).thenReturn(inventory);

		potion2.use(character);

		// Consumption of potion2 requires these 2 calls.
		verify(inventory).cleanup();
		verify(character).setPoisoned(false);
	}

	@Test
	public void testUseWithoutCleanup() {
		final Inventory inventory = mock(Inventory.class);
		final Character character = mock(Character.class);
		when(character.getInventory()).thenReturn(inventory);

		potion1.use(character);

		// Consumption of potion1 requires just a call to setPoisoned.
		verify(character).setPoisoned(false);
	}

	@Test
	public void testClone() {
		final PoisonPotion clone = (PoisonPotion) potion1.clone();

		// Make sure all fields match
		assertEquals(potion1.amount, clone.amount);
		assertEquals(potion1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(potion1, clone);


		final PoisonPotion clone2 = (PoisonPotion) potion2.clone();

		// Make sure all fields match
		assertEquals(potion2.amount, clone2.amount);
		assertEquals(potion2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(potion2, clone2);
	}

}
