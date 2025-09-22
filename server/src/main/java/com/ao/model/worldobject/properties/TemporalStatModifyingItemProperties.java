package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.ObjectType;

import java.util.List;

/**
 * Defines an Item that modifies a user's stat's properties for a given period of time. Allows a lightweight pattern
 * implementation.
 */

public class TemporalStatModifyingItemProperties extends StatModifyingItemProperties {

    protected int effectTime;

    /**
     * Creates a new StatModifyingItemProperties instance.
     *
     * @param type                type of the item
     * @param id                  id of the item
     * @param name                name of the item
     * @param graphic             graphic for the item
     * @param value               item's value
     * @param forbiddenArchetypes list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces      list of Races not allowed to use this item
     * @param newbie              whether the item is newbie or not
     * @param noLog               whether this item should be logged or not
     * @param falls               whether this item falls or not
     * @param respawnable         whether this item respawns or not when in a merchant npc inventory
     * @param minModifier         minimum amount by which the stats are to be modified
     * @param maxModifier         maximum amount by which the stats are to be modified
     * @param effectTime          time for which the effect is valid
     */
    // @param tradeable True if it's tradeable, false otherwise
    public TemporalStatModifyingItemProperties(ObjectType type, int id, String name, int graphic, int value, List<UserArchetype> forbiddenArchetypes,
                                               List<Race> forbiddenRaces, boolean newbie, boolean noLog, boolean falls, boolean respawnable,
                                               int minModifier, int maxModifier, int effectTime) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, minModifier, maxModifier);
        this.effectTime = effectTime;
    }

    public int getEffectDuration() {
        return effectTime;
    }

}
