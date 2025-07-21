package com.ao.model.character.movement;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;

/**
 * Dummy movement strategy, just stay quiet.
 */
public class QuietMovementStrategy implements MovementStrategy {

	@Override
	public Heading move(Position pos) {
		// No, I will not.
		
		return null;
	}

	@Override
	public void setTarget(Character target) {
		// I don't care the target, I will never move!
	}

	@Override
	public void setTarget(Position pos) {
		// I don't care the target, I will never move!
	}

}
