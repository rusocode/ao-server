package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.character.UserCharacter;
import com.ao.model.worldobject.WorldObject;

public class InvisibilityEffect implements Effect {

    @Override
    public void apply(Character caster, Character target) {
        target.setInvisible(true);
    }

    @Override
    public boolean appliesTo(Character caster, Character target) {
        if (!(target instanceof UserCharacter) || target.isDead()) return false;
        return true;
    }

    @Override
    public boolean appliesTo(Character caster, WorldObject worldobject) {
        return false;
    }

    @Override
    public void apply(Character caster, WorldObject target) {
        throw new InvalidTargetException();
    }

}
