package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

/**
 * An effect that poisons a character.
 */

public class PoisonEffect implements Effect {

    /*
     * (non-Javadoc)
     * @see com.ao.model.spell.effect.Effect#apply(ao.model.character.Character, com.ao.model.character.Character)
     */
    @Override
    public void apply(Character caster, Character target) {
        target.setPoisoned(true);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.spell.effect.Effect#appliesTo(ao.model.character.Character, com.ao.model.character.Character)
     */
    @Override
    public boolean appliesTo(Character caster, Character target) {
        return !target.isDead();
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
