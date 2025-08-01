package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines an Item's properties. Allows a lightweight pattern implementation.
 */

public class ItemProperties extends WorldObjectProperties {

    protected int value;
    protected boolean newbie;

    protected List<UserArchetype> forbiddenArchetypes;
    protected List<Race> forbiddenRaces;

    protected boolean noLog;
    protected boolean falls;
    protected boolean respawnable;

    /**
     * Creates a new ItemProperties instance.
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
     */
    // @param tradeable true if it's tradeable, false otherwise
    public ItemProperties(WorldObjectType type, int id, String name, int graphic,
                          int value, List<UserArchetype> forbiddenArchetypes,
                          List<Race> forbiddenRaces, boolean newbie,
                          boolean noLog, boolean falls, boolean respawnable) {

        super(type, id, name, graphic);
        this.value = value;
        this.forbiddenArchetypes = forbiddenArchetypes;
        this.forbiddenRaces = forbiddenRaces;
        this.newbie = newbie;
        this.noLog = noLog;
        this.falls = falls;
        this.respawnable = respawnable;
    }

    public int getValue() {
        return value;
    }

    public boolean isNewbie() {
        return newbie;
    }

    public List<UserArchetype> getForbiddenArchetypes() {
        return forbiddenArchetypes;
    }

    public List<Race> getForbiddenRaces() {
        return forbiddenRaces;
    }

    public boolean isNoLog() {
        return noLog;
    }

    public boolean isFalls() {
        return falls;
    }

    public boolean isRespawnable() {
        return respawnable;
    }

}
