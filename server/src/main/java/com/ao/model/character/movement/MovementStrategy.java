package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;

public interface MovementStrategy {

    /**
     * Applies the movement strategy.
     *
     * @param pos current position.
     * @return null if there is no need to move
     */
    Heading move(Position pos);

    /**
     * Targets a new character.
     *
     * @param target character to target
     */
    void setTarget(Character target);

    /**
     * Targets a position.
     *
     * @param pos final destination position
     */
    void setTarget(Position pos);

}
