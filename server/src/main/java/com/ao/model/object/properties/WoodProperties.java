package com.ao.model.object.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.WoodType;
import com.ao.model.object.ObjectType;

import java.util.List;

/**
 * Defines the wood's properties. Allows a lightweight pattern implementation.
 */

public class WoodProperties extends ItemProperties {

    protected WoodType woodType;

    /**
     * Creates a new WoodProperties instance.
     *
     * @param type                type of the item
     * @param id                  id of the item
     * @param name                name of the item
     * @param graphic             graphic for the item
     * @param value               item's value
     * @param forbiddenArchetypes list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces      list of Races not allowed to use this item.
     * @param newbie              whether the item is newbie or not
     * @param noLog               whether this item should be logged or not
     * @param falls               whether this item falls or not
     * @param respawnable         whether this item respawns or not when in a merchant npc inventory
     * @param woodType            type of wood this is
     */
    // @param tradeable true if it's tradeable, false otherwise
    public WoodProperties(ObjectType type, int id, String name, int graphic, int value, List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces,
                          boolean newbie, boolean noLog, boolean falls, boolean respawnable, WoodType woodType) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable);
        this.woodType = woodType;
    }

    public WoodType getWoodType() {
        return woodType;
    }

}
