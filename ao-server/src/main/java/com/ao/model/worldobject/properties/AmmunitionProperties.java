package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines an Equippable Item's properties. Allows a lightweight pattern implementation.
 */

public class AmmunitionProperties extends EquippableItemProperties {

    protected int minHit;
    protected int maxHit;

    // TODO add poison and paralizing modifiers!

    /**
     * Creates a new AmmunitionProperties instance.
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
     * @param respawnable         whether this item respawns or not when in a merchant NPC's inventory
     * @param equippedGraphic     id of the grpahic to display when equipped
     * @param minHit              minimum hit done by this item
     * @param maxHit              maximum hit done by this item
     */
    // @param tradeable true if it's tradeable, false otherwise
    public AmmunitionProperties(WorldObjectType type, int id, String name, int graphic,
                                int value, List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces,
                                boolean newbie, boolean noLog, boolean falls, boolean respawnable,
                                int equippedGraphic, int minHit, int maxHit) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);
        this.maxHit = maxHit;
        this.minHit = minHit;
    }

    public int getMinHit() {
        return minHit;
    }

    public int getMaxHit() {
        return maxHit;
    }

}
