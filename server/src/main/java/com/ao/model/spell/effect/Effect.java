package com.ao.model.spell.effect;

import com.ao.model.character.Character;
import com.ao.model.object.Object;

public interface Effect {

    /**
     * Applies the effect in the given character.
     *
     * @param caster character casting the spell with the effect
     * @param target character where to apply the effect
     */
    void apply(Character caster, Character target);

    /**
     * Applies the effect in the given object
     *
     * @param caster character casting the spell with the effect
     * @param target world object on which to apply the effect
     */
    void apply(Character caster, Object target);

    /**
     * Checks whether the effect can be applied to the given character or not.
     *
     * @param caster caster character
     * @param target target character
     * @return true if the effect can be applied, false otherwise
     */
    boolean appliesTo(Character caster, Character target);

    /**
     * Checks whether the effect can be applied to the given world object or not.
     *
     * @param caster      caster of the spell
     * @param worldobject target object
     * @return true if the effect can be applied, false otherwise
     */
    boolean appliesTo(Character caster, Object worldobject);

}
