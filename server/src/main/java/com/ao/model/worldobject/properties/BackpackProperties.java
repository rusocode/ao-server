package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines a Backpack's properties. Allows a lightweight pattern implementation.
 */

public class BackpackProperties extends EquippableItemProperties {

    protected int slots;

    /**
     * Creates a new BackpackProperties instance.
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
     * @param slots               slots to be added
     */
    // @param tradeable true if it's tradeable, false otherwise
    public BackpackProperties(WorldObjectType type, int id, String name, int graphic,
                              int value, List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces,
                              boolean newbie, boolean noLog, boolean falls, boolean respawnable,
                              int equippedGraphic, int slots) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);
        this.slots = slots;
    }

    public int getSlots() {
        return slots;
    }

}
