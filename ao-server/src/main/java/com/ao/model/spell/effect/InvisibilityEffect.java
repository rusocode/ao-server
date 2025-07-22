package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.character.UserCharacter;
import com.ao.model.worldobject.WorldObject;

/**
 * An effect to turn characters invisible.
 */

public class InvisibilityEffect implements Effect {

    /*
     * (non-Javadoc)
     * @see com.ao.model.spell.effect.Effect#apply(ao.model.character.Character, com.ao.model.character.Character)
     */
    @Override
    public void apply(Character caster, Character target) {
        target.setInvisible(true);
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.spell.effect.Effect#appliesTo(ao.model.character.Character, com.ao.model.character.Character)
     */
    @Override
    public boolean appliesTo(Character caster, Character target) {
        if (!(target instanceof UserCharacter) || target.isDead()) return false;
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
