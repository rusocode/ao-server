package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GreedyMovementStrategyTest {

    private final MovementStrategy movement = new GreedyMovementStrategy();

    @Test
    public void testMove() {
        Position pos = new Position((byte) 50, (byte) 50, 1);
        Position target = new Position((byte) 60, (byte) 60, 1);

        // Should go to the northeast
        moveTest(pos, target, Heading.WEST, Heading.SOUTH);
        moveTestCharacter(pos, target, Heading.WEST, Heading.SOUTH);

        target.setY((byte) 20);

        // Should go to the southeast
        moveTest(pos, target, Heading.WEST, Heading.NORTH);
        moveTestCharacter(pos, target, Heading.WEST, Heading.NORTH);

        target.setX((byte) 20);

        // Should go to the southwest
        moveTest(pos, target, Heading.EAST, Heading.NORTH);
        moveTestCharacter(pos, target, Heading.EAST, Heading.NORTH);

        target.setY((byte) 60);

        // Should go to the northwest
        moveTest(pos, target, Heading.EAST, Heading.SOUTH);
        moveTestCharacter(pos, target, Heading.EAST, Heading.SOUTH);
    }

    private void moveTest(Position pos, Position target, Heading shouldnt1, Heading shouldnt2) {
        movement.setTarget(target);
        _moveTest(pos, target, shouldnt1, shouldnt2);
    }

    private void moveTestCharacter(Position pos, Position target, Heading shouldnt1, Heading shouldnt2) {
        Character character = mock(Character.class);
        when(character.getPosition()).thenReturn(target);
        movement.setTarget(character);
        _moveTest(pos, target, shouldnt1, shouldnt2);
    }

    private void _moveTest(Position pos, Position target, Heading shouldnt1, Heading shouldnt2) {
        // Save these values because they will change, and we don't want to modify the original object
        byte x = pos.getX();
        byte y = pos.getY();

        int steps = pos.getDistance(target);

        for (int i = 0; i < steps; i++) {
            Heading move = movement.move(pos);
            movePosition(pos, move);

            assertThat(move).isNotNull();
            assertThat(shouldnt1).isNotSameAs(move);
            assertThat(shouldnt2).isNotSameAs(move);
        }

        // Has arrived to target
        assertThat(pos.getX()).isEqualTo(target.getX());
        assertThat(pos.getY()).isEqualTo(target.getY());
        assertThat(movement.move(pos)).isNull();

        pos.setX(x);
        pos.setY(y);
    }

    private void movePosition(Position pos, Heading direction) {
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
