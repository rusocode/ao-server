

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.worldobject.properties.KeyProperties;

public class KeyTest extends AbstractWorldObjectTest {

	private static final int CODE = 123;
	
	private Key key1;
	
	@Before
	public void setUp() throws Exception {
		final KeyProperties props1 = new KeyProperties(WorldObjectType.KEY, 1, "Llave maestra", 1, 1, 0, null, null, false, false, false, false, CODE);
		key1 = new Key(props1, 1);
		
		object = key1;
		objectProps = props1;
	}
	
	@Test
	public void testClone() {
		final Key clone = (Key) key1.clone();
		
		// Make sure all fields match
		assertEquals(key1.amount, clone.amount);
		assertEquals(key1.properties, clone.properties);
		
		// Make sure the object itself is different
		assertNotSame(key1, clone);

	}
	
	@Test
	public void testGetMinHit() {
		assertEquals(CODE, key1.getCode());
	}
	
}
