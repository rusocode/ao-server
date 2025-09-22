package com.ao.model.object.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.ObjectType;

import java.util.List;

/**
 * Defines a Musical Instrument's properties. Allows a lightweight pattern implementation.
 */

public class MusicalInstrumentProperties extends EquippableItemProperties {

    protected List<Integer> sounds;

    /**
     * Creates a new MusicalInstrumentProperties instance.
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
     * @param equippedGraphic     id of the grpahic to display when equipped
     * @param sounds              possible sounds to be reproduced by this isntrument
     */
    // @param tradeable true if it's tradeable, false otherwise
    public MusicalInstrumentProperties(ObjectType type, int id, String name, int graphic, int value, List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces,
                                       boolean newbie, boolean noLog, boolean falls, boolean respawnable, int equippedGraphic, List<Integer> sounds) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);
        this.sounds = sounds;
    }

    public List<Integer> getSounds() {
        return sounds;
    }

}
