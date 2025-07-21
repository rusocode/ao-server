

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ao.model.worldobject.properties.TeleportProperties;

public class TeleportTest extends AbstractWorldObjectTest {

	private static final int RADIUS = 4;

	private Teleport teleport1;

	@Before
	public void setUp() throws Exception {
		final TeleportProperties props1 = new TeleportProperties(WorldObjectType.TELEPORT, 1, "Teleport", 1, RADIUS);
		teleport1 = new Teleport(props1);

		object = teleport1;
		objectProps = props1;
	}

	@Test
	public void testGetRadius() {
		assertEquals(RADIUS, teleport1.getRadius());
	}
}
