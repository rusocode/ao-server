package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines a Boat's properties. Allows a lightweight pattern implementation.
 */

public class BoatProperties extends DefensiveItemProperties {

    protected int minHit;
    protected int maxHit;

    protected int navigationSkill;

    /**
     * Creates a new BoatProperties instance.
     *
     * @param type                  type of the item
     * @param id                    id of the item
     * @param name                  name of the item
     * @param graphic               graphic for the item
     * @param value                 item's value
     * @param navigationSkill       item's usage difficulty
     * @param manufactureDifficulty item's manufacture difficulty
     * @param forbiddenArchetypes   list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces        list of Races not allowed to use this item
     * @param newbie                whether the item is newbie or not
     * @param noLog                 whether this item should be logged or not
     * @param falls                 whether this item falls or not
     * @param respawnable           whether this item respawns or not when in a merchant NPC's inventory
     * @param equippedGraphic       id of the grpahic to display when equipped
     * @param minDef                minimum defense granted by this item
     * @param maxDef                maximum defense granted by this item
     * @param minMagicDef           minimum magic defense granted by this item
     * @param maxMagicDef           maximum magic defense granted by this item
     * @param minHit                minimum hit granted by this boat
     * @param maxHit                maximum hit granted by this boat
     */
    public BoatProperties(WorldObjectType type, int id, String name, int graphic, int value, int navigationSkill, int manufactureDifficulty,
                          List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie,
                          boolean noLog, boolean falls, boolean respawnable,
                          int equippedGraphic, int minDef, int maxDef, int minMagicDef,
                          int maxMagicDef, int minHit, int maxHit) {
        super(type, id, name, graphic, value,
                manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic,
                minDef, maxDef, minMagicDef, maxMagicDef);
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
