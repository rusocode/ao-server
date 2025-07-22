package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Position;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class QuietMovementStrategyTest {

    private final MovementStrategy movement = new QuietMovementStrategy();

    @Test
    public void testMove() {
        final Position pos = new Position((byte) 50, (byte) 50, 1);
        final Position target = new Position((byte) 60, (byte) 60, 1);

        movement.setTarget(target);

        assertNull(movement.move(pos));

        final Character character = mock(Character.class);

        movement.setTarget(character);

        assertNull(movement.move(pos));
    }

}
