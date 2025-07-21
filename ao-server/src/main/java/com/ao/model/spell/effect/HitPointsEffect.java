package com.ao.model.spell.effect;

import java.util.Random;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

/**
 * An effect that alters HP (either increases or decreases)
 */
public class HitPointsEffect implements Effect {

	private static final float EFFECT_MULTIPLIER = 0.03f;
	
	private static final Random randomGenerator = new Random();
	
	private int minPoints;
	private int deltaPoints;
	
	/**
	 * Creates a new HitPointsEffect instance.
	 * @param minPoints The minimum points to be added / subtracted (if negative)
	 * @param maxPoints The maximum points to be added / subtracted (if negative)
	 */
 	public HitPointsEffect(int minPoints, int maxPoints) {
		this.minPoints = minPoints;
		this.deltaPoints = maxPoints - minPoints;
	}

 	/*
 	 * (non-Javadoc)
 	 * @see com.ao.model.spell.effect.Effect#apply(ao.model.character.Character, com.ao.model.character.Character)
 	 */
	@Override
	public void apply(Character caster, Character target) {
		int points = minPoints + randomGenerator.nextInt(deltaPoints);
		points += (int) Math.round(points * EFFECT_MULTIPLIER * caster.getLevel());
	    
		target.addToHitPoints(points);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.spell.effect.Effect#appliesTo(ao.model.character.Character, com.ao.model.character.Character)
	 */
	@Override
	public boolean appliesTo(Character caster, Character target) {
		if (target.isDead()) {
			return false;
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.spell.effect.Effect#appliesTo(ao.model.character.Character, com.ao.model.worldobject.WorldObject)
	 */
	@Override
	public boolean appliesTo(Character caster, WorldObject worldobject) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.spell.effect.Effect#apply(ao.model.character.Character, com.ao.model.worldobject.WorldObject)
	 */
	@Override
	public void apply(Character caster, WorldObject target) {
		throw new InvalidTargetException();
	}

}
