package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.ObjectType;

import java.util.List;

/**
 * Defines an Item that modifies a user's stat's properties and is reloadable. Allows a lightweight pattern implementation.
 */

public class RefillableStatModifyingItemProperties extends StatModifyingItemProperties {

    protected boolean filled;
    protected RefillableStatModifyingItemProperties otherStateProperties;

    /**
     * Creates a new StatModifyingItemProperties instance.
     *
     * @param type                 type of the item
     * @param id                   id of the item
     * @param name                 name of the item
     * @param graphic              graphic for the item
     * @param value                item's value
     * @param forbiddenArchetypes  list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces       list of Races not allowed to use this item
     * @param newbie               whether the item is newbie or not
     * @param noLog                whether this item should be logged or not
     * @param falls                whether this item falls or not
     * @param respawnable          whether this item respawns or not when in a merchant npc inventory
     * @param minModifier          minimum amount by which the stats is to be modified
     * @param maxModifier          maximum amount by which the stats is to be modified
     * @param filled               true if the object is filled, false if empty
     * @param otherStateProperties object properties to be used in the other state
     */
    // @param tradeable true if it's tradeable, false otherwise
    public RefillableStatModifyingItemProperties(ObjectType type, int id, String name, int graphic, int value, List<UserArchetype> forbiddenArchetypes,
                                                 List<Race> forbiddenRaces, boolean newbie, boolean noLog, boolean falls, boolean respawnable, int minModifier, int maxModifier,
                                                 boolean filled, RefillableStatModifyingItemProperties otherStateProperties) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, minModifier, maxModifier);

        this.filled = filled;

        // If the other state is set, link them together
        if (otherStateProperties != null) setOtherStateProperties(otherStateProperties);

    }

    /**
     * @return the other state object properties
     */
    public RefillableStatModifyingItemProperties getOtherStateProperties() {
        return otherStateProperties;
    }

    /**
     * Links this object with the one for the other state (empty or filled).
     *
     * @param props properties of the object to which to link it
     */
    private void setOtherStateProperties(RefillableStatModifyingItemProperties props) {
        otherStateProperties = props;
        props.otherStateProperties = this;
    }

    /**
     * @return whether the object is filled or empty
     */
    public boolean isFilled() {
        return filled;
    }

}
