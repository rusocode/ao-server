

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;

public class GrabablePropTest extends AbstractItemTest {

	private GrabableProp prop1;
	private GrabableProp prop2;

	@Before
	public void setUp() throws Exception {
		final ItemProperties props1 = new ItemProperties(WorldObjectType.GRABABLE_PROP, 1, "Black Potion", 1, 1, null, null, false, true, false, true);
		prop1 = new GrabableProp(props1, 5);

		final ItemProperties props2 = new ItemProperties(WorldObjectType.GRABABLE_PROP, 1, "Black Potion", 1, 1, null, null, false, false, false, true);
		prop2 = new GrabableProp(props2, 1);

		object = prop2;
		objectProps = props2;
		ammount = 1;
	}

	@Test
	public void testUse() {
		final Character character = mock(Character.class);

		// nothing should happen
		prop1.use(character);
		prop2.use(character);

		verifyZeroInteractions(character);
	}

	@Test
	public void testClone() {
		final GrabableProp clone = (GrabableProp) prop1.clone();

		// Make sure all fields match
		assertEquals(prop1.amount, clone.amount);
		assertEquals(prop1.properties, clone.properties);

		// Make sure the object itself is different
		assertNotSame(prop1, clone);


		final GrabableProp clone2 = (GrabableProp) prop2.clone();

		// Make sure all fields match
		assertEquals(prop2.amount, clone2.amount);
		assertEquals(prop2.properties, clone2.properties);

		// Make sure the object itself is different
		assertNotSame(prop2, clone2);
	}
}
