package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.Object;

public class ImmobilizationEffect implements Effect {

    @Override
    public void apply(Character caster, Character target) {
        target.setImmobilized(true);
    }

    @Override
    public boolean appliesTo(Character caster, Character target) {
        return !target.isDead();
    }

    @Override
    public boolean appliesTo(Character caster, Object worldobject) {
        return false;
    }

    @Override
    public void apply(Character caster, Object target) {
        throw new InvalidTargetException();
    }

}
