package com.ao.model.spell.effect;

import com.ao.model.character.Character;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.Object;

public class MimetismEffect implements Effect {

    @Override
    public boolean appliesTo(Character caster, Character target) {
        // Only druids (or maybe npcs, not yet defined) can mimetize themselves with npcs
        if (!(target instanceof UserCharacter) && (caster instanceof UserCharacter) && ((UserCharacter) caster).getArchetype() != UserArchetype.DRUID.getArchetype())
            return false;
        return true;
    }

    @Override
    public boolean appliesTo(Character caster, Object worldobject) {
        // Only druids (or maybe npcs, not yet defined) can mimetize themselves with world objects
        if (caster instanceof UserCharacter && ((UserCharacter) caster).getArchetype() != UserArchetype.DRUID.getArchetype())
            return false;
        return true;
    }

    @Override
    public void apply(Character caster, Character target) {
    }

    @Override
    public void apply(Character caster, Object target) {
    }

}
