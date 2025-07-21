

package com.ao.model.character.movement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;

public class GreedyMovementStrategyTest {

	private MovementStrategy movement = new GreedyMovementStrategy();

	@Test
	public void testMove() {
		final Position pos = new Position((byte) 50, (byte) 50, 1);
		final Position target = new Position((byte) 60, (byte) 60, 1);

		// Should go to northeast
		moveTest(pos, target, Heading.WEST, Heading.SOUTH);
		moveTestCharacter(pos, target, Heading.WEST, Heading.SOUTH);

		target.setY((byte) 20);

		// Should go to southeast
		moveTest(pos, target, Heading.WEST, Heading.NORTH);
		moveTestCharacter(pos, target, Heading.WEST, Heading.NORTH);

		target.setX((byte) 20);

		// Should go to southwest
		moveTest(pos, target, Heading.EAST, Heading.NORTH);
		moveTestCharacter(pos, target, Heading.EAST, Heading.NORTH);

		target.setY((byte) 60);

		// Should go to northwest
		moveTest(pos, target, Heading.EAST, Heading.SOUTH);
		moveTestCharacter(pos, target, Heading.EAST, Heading.SOUTH);
	}

	private void moveTest(final Position pos, final Position target, final Heading shouldnt1, final Heading shouldnt2) {
		movement.setTarget(target);

		_moveTest(pos, target, shouldnt1, shouldnt2);
	}

	private void moveTestCharacter(final Position pos, final Position target, final Heading shouldnt1, final Heading shouldnt2) {
		final Character character = mock(Character.class);
		when(character.getPosition()).thenReturn(target);

		movement.setTarget(character);

		_moveTest(pos, target, shouldnt1, shouldnt2);
	}

	private void _moveTest(final Position pos, final Position target, final Heading shouldnt1, final Heading shouldnt2) {
		// Save these values because they will change and we don't want to modify the original object.
		final byte x = pos.getX();
		final byte y = pos.getY();

		int steps = pos.getDistance(target);

		for (int i = 0; i < steps; i++) {
			final Heading move = movement.move(pos);
			movePosition(pos, move);

			assertNotNull(move);
			assertNotSame(shouldnt1, move);
			assertNotSame(shouldnt2, move);
		}

		// Has arrived to target.
		assertEquals(target.getX(), pos.getX());
		assertEquals(target.getY(), pos.getY());
		assertNull(movement.move(pos));

		pos.setX(x);
		pos.setY(y);
	}

	private void movePosition(final Position pos, final Heading direction) {
		switch (direction) {
		case NORTH:
			pos.setY((byte) (pos.getY() + 1));
			break;

		case SOUTH:
			pos.setY((byte) (pos.getY() - 1));
			break;

		case EAST:
			pos.setX((byte) (pos.getX() + 1));
			break;

		case WEST:
			pos.setX((byte) (pos.getX() - 1));
			break;
		}
	}

}
