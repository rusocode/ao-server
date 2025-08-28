package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class QuietMovementStrategyTest {

    private final MovementStrategy movement = new QuietMovementStrategy();

    @Test
    public void testMove() {
        Position pos = new Position((byte) 50, (byte) 50, 1);
        Position target = new Position((byte) 60, (byte) 60, 1);

        movement.setTarget(target);

        assertThat(movement.move(pos)).isNull();

        Character character = mock(Character.class);

        movement.setTarget(character);

        assertThat(movement.move(pos)).isNull();
    }

}
