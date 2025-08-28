package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

/**
 * An effects that recovers a character's mobility (counters palaysis and immobility).
 */

public class RecoverMobilityEffect implements Effect {

    @Override
    public void apply(Character caster, Character target) {
        target.setImmobilized(false);
        target.setParalyzed(false);
    }

    @Override
    public boolean appliesTo(Character caster, Character target) {
        // TODO Considerar casos de remo a npc cuando esté armado el sistema de mascotas
        if (!target.isImmobilized() && !target.isParalyzed()) return false;
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
