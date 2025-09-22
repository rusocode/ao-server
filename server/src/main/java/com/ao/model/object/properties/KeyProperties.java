package com.ao.model.object.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.ObjectType;

import java.util.List;

/**
 * Defines a Key's properties. Allows a lightweight pattern implementation.
 */

public class KeyProperties extends ItemProperties {

    protected int code;

    /**
     * Creates a new KeyProperties instance.
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
     * @param code                key's code
     */
    // @param tradeable true if it's tradeable, false otherwise
    public KeyProperties(ObjectType type, int id, String name, int graphic, int value, int manufactureDifficulty, List<UserArchetype> forbiddenArchetypes,
                         List<Race> forbiddenRaces, boolean newbie, boolean noLog, boolean falls, boolean respawnable, int code) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
