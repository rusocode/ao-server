package com.ao.model.spell.effect;

import com.ao.model.character.Character;
import com.ao.model.character.UserCharacter;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObject;

public class MimetismEffect implements Effect {

    @Override
    public boolean appliesTo(Character caster, Character target) {
        // Only druids (or maybe npcs, not yet defined) can mimetize themselves with NPC's
        if (!(target instanceof UserCharacter) && (caster instanceof UserCharacter) && ((UserCharacter) caster).getArchetype() != UserArchetype.DRUID.getArchetype())
            return false;
        return true;
    }

    @Override
    public boolean appliesTo(Character caster, WorldObject worldobject) {
        // Only druids (or maybe npcs, not yet defined) can mimetize themselves with world objects
        if (caster instanceof UserCharacter && ((UserCharacter) caster).getArchetype() != UserArchetype.DRUID.getArchetype())
            return false;
        return true;
    }

    @Override
    public void apply(Character caster, Character target) {
    }

    @Override
    public void apply(Character caster, WorldObject target) {
    }

}
