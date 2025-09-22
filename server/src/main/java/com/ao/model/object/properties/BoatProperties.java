package com.ao.model.object.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.ObjectType;

import java.util.List;

/**
 * Defines a Boat's properties. Allows a lightweight pattern implementation.
 */

public class BoatProperties extends DefensiveItemProperties {

    protected int minHit, maxHit;

    protected int navigationSkill;


    public BoatProperties(ObjectType type, int id, String name, int graphic, int value, int navigationSkill, int craftingSkillPoints,
                          List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie, boolean noLog, boolean falls, boolean respawnable,
                          int equippedGraphic, int minDef, int maxDef, int minMagicDef, int maxMagicDef, int minHit, int maxHit) {
        super(type, id, name, graphic, value, craftingSkillPoints, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic, minDef, maxDef, minMagicDef, maxMagicDef);
        this.minHit = minHit;
        this.maxHit = maxHit;
        this.navigationSkill = navigationSkill;
    }

    public int getMinHit() {
        return minHit;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getNavigationSkill() {
        return navigationSkill;
    }

}
