

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

public class GoldTest extends AbstractItemTest {

	private Gold gold1;
	private Gold gold2;

	@Before
	public void setUp() throws Exception {
		final ItemProperties props = new ItemProperties(WorldObjectType.MONEY, 13, "Monedas de oro", 1, 1, null, null, false, true, true, true);
		gold1 = new Gold(props, 1000);
		gold2 = new Gold(props, 2000);

		object = gold1;
		objectProps = props;
		ammount = 1000;
	}

	@Test
	public void testUse() {
		final Character character = mock(Character.class);
		final Inventory inventory = mock(Inventory.class);
		when(character.getInventory()).thenReturn(inventory);

		gold1.use(character);

		verify(inventory).cleanup();
		verify(character).addMoney(1000);
	}

	@Test
	public void testClone() {
		final Gold clone = (Gold) gold1.clone();

		assertEquals(gold1.getAmount(), clone.getAmount());
		assertNotSame(clone, gold1);

		final Gold clone2 = (Gold) gold2.clone();

		assertEquals(gold2.getAmount(), clone2.getAmount());
		assertNotSame(clone2, gold2);
	}
}
