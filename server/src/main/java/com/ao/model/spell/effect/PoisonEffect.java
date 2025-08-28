package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

public class PoisonEffect implements Effect {

    @Override
    public void apply(Character caster, Character target) {
        target.setPoisoned(true);
    }

    @Override
    public boolean appliesTo(Character caster, Character target) {
        return !target.isDead();
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
