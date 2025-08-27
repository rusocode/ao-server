package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines a Defensive Item's properties. Allows a lightweight pattern implementation.
 */

public class DefensiveItemProperties extends EquippableItemProperties {

    protected int minDef, maxDef;
    protected int minMagicDef, maxMagicDef;

    /**
     * Creates a new DefensiveItemProperties instance.
     *
     * @param type                type of the item
     * @param id                  id of the item
     * @param name                name of the item
     * @param graphic             graphic for the item
     * @param value               item's value
     * @param forbiddenArchetypes List of UserArchetypes not allowed to use this item
     * @param forbiddenRaces      List of Races not allowed to use this item
     * @param newbie              whether the item is newbie or not
     * @param noLog               whether this item should be logged or not
     * @param falls               whether this item falls or not
     * @param respawnable         whether this item respawns or not when in a merchant NPC's inventory
     * @param equippedGraphic     id of the grpahic to display when equipped
     * @param minDef              minimum defense granted by this item
     * @param maxDef              maximum defense granted by this item
     * @param minMagicDef         minimum magic defense granted by this item
     * @param maxMagicDef         maximum magic defense granted by this item
     */
    // @param tradeable true if it's tradeable, false otherwise
    public DefensiveItemProperties(WorldObjectType type, int id, String name, int graphic,
                                   int value, int manufactureDifficulty,
                                   List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie,
                                   boolean noLog, boolean falls, boolean respawnable,
                                   int equippedGraphic, int minDef, int maxDef, int minMagicDef, int maxMagicDef) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);
        this.minDef = minDef;
        this.maxDef = maxDef;
        this.minMagicDef = minMagicDef;
        this.maxMagicDef = maxMagicDef;
    }

    public int getMinDef() {
        return minDef;
    }

    public int getMaxDef() {
        return maxDef;
    }

    public int getMinMagicDef() {
        return minMagicDef;
    }

    public int getMaxMagicDef() {
        return maxMagicDef;
    }

}
