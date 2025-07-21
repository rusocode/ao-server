

package com.ao.model.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

	private static final byte X_POSITION = 50;
	private static final byte Y_POSITION = 50;

	private Position pos;

	@Before
	public void setUp() {
		pos = new Position(X_POSITION, Y_POSITION, 1);
	}

	@Test
	public void testAddToX() {
		pos.addToX(7);

		assertEquals(X_POSITION + 7, pos.getX());

		pos.addToX(-6);

		assertEquals(X_POSITION + 1, pos.getX());
	}

	@Test
	public void testAddToY() {
		pos.addToY(7);

		assertEquals(Y_POSITION + 7, pos.getY());

		pos.addToY(-6);

		assertEquals(Y_POSITION + 1, pos.getY());
	}

	@Test
	public void testGetDistance() {
		Position anotherPos = new Position((byte) (X_POSITION +  20), (byte) (Y_POSITION +  20), 1);

		assertEquals(40, pos.getDistance(anotherPos));
	}

	@Test
	public void testInVisionRange() {
		Position anotherPos = new Position((byte) (X_POSITION +  20), (byte) (Y_POSITION +  20), pos.getMap());

		assertFalse(pos.inVisionRange(anotherPos));

		anotherPos.setX((byte) (X_POSITION + 5));
		anotherPos.setY((byte) (Y_POSITION + 5));

		assertTrue(pos.inVisionRange(anotherPos));

		anotherPos.setMap(2);

		assertFalse(pos.inVisionRange(anotherPos));
	}

}
