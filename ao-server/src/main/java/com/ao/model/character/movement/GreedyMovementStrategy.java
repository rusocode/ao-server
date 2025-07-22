package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;

import java.util.Random;

public class GreedyMovementStrategy implements MovementStrategy {

    private final Random randomGenerator = new Random();
    private Position targetPosition;
    private Character targetCharacter;

    @Override
    public Heading move(Position pos) {
        if (targetCharacter != null) targetPosition = targetCharacter.getPosition();

        int x = (int) pos.getX() - targetPosition.getX();
        int y = (int) pos.getY() - targetPosition.getY();

        if (x < 0 && y < 0) return randomGenerator.nextInt(2) == 0 ? Heading.NORTH : Heading.EAST; // Northeast
        else if (x > 0 && y < 0) return randomGenerator.nextInt(2) == 0 ? Heading.NORTH : Heading.WEST; // Northwest
        else if (x > 0 && y > 0) return randomGenerator.nextInt(2) == 0 ? Heading.SOUTH : Heading.WEST; // Southwest
        else if (x < 0 && y > 0) return randomGenerator.nextInt(2) == 0 ? Heading.SOUTH : Heading.EAST; // Southeast
        else if (x == 0 && y < 0) return Heading.NORTH; // North
        else if (x == 0 && y > 0) return Heading.SOUTH; // South
        else if (x > 0 && y == 0) return Heading.WEST; // West
        else if (x < 0 && y == 0) return Heading.EAST; // East

        // Position and targetPos are the same position
        return null;
    }

    @Override
    public void setTarget(Character target) {
        targetCharacter = target;
    }

    @Override
    public void setTarget(Position pos) {
        targetPosition = pos;
    }

}
