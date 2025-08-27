package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines a Staff's properties. Allows a lightweight pattern implementation.
 */

public class StaffProperties extends WeaponProperties {

    protected int magicPower;
    protected int damageBonus;

    /**
     * Creates a new StaffProperties instance.
     *
     * @param type                  type of the item
     * @param id                    id of the item
     * @param name                  name of the item
     * @param graphic               graphic for the item
     * @param value                 item's value
     * @param manufactureDifficulty item's manufacture difficulty
     * @param forbiddenArchetypes   list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces        list of Races not allowed to use this item
     * @param newbie                whether the item is newbie or not
     * @param noLog                 whether this item should be logged or not
     * @param falls                 whether this item falls or not
     * @param respawnable           whether this item respawns or not when in a merchant NPC's inventory
     * @param equippedGraphic       id of the grpahic to display when equipped
     * @param stabs                 whether if this item stabs or not
     * @param piercingDamage        pircing damage (not reduced by any kind of armor or defense) done by this item
     * @param minHit                minimum hit done by this item
     * @param maxHit                maximum hit done by this item
     * @param magicPower            magic power of the staff
     * @param damageBonus           attack bonus of the item
     */
    public StaffProperties(WorldObjectType type, int id, String name, int graphic, int value, int manufactureDifficulty,
                           List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie, boolean noLog, boolean falls, boolean respawnable,
                           int equippedGraphic, boolean stabs, int piercingDamage, int minHit, int maxHit, int magicPower, int damageBonus) {
        super(type, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable,
                equippedGraphic, stabs, piercingDamage, minHit, maxHit);
        this.damageBonus = damageBonus;
        this.magicPower = magicPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

}
