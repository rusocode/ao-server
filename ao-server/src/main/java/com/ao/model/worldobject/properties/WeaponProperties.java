package com.ao.model.worldobject.properties;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

import java.util.List;

/**
 * Defines a Weapon's properties. Allows a lightweight pattern implementation.
 */

public class WeaponProperties extends EquippableItemProperties {

    protected boolean stabs;
    protected int piercingDamage; // This replaces the old "Refuerzo"

    protected int minHit;
    protected int maxHit;

    // TODO add poison modifier!

    /**
     * Creates a new WeaponProperties instance.
     *
     * @param type                type of the item
     * @param id                  id of the item
     * @param name                name of the item
     * @param graphic             graphic for the item
     * @param value               item's value
     * @param forbiddenArchetypes list of UserArchetypes not allowed to use this item
     * @param forbiddenRaces      list of Races not allowed to use this item
     * @param newbie              whether the item is a newbie or not
     * @param noLog               whether this item should be logged or not
     * @param falls               whether this item falls or not
     * @param respawnable         whether this item respawns or not when in a merchant NPC's inventory
     * @param equippedGraphic     id of the grpahic to display when equipped
     * @param stabs               whether if this item stabs or not
     * @param piercingDamage      pircing damage (not reduced by any kind of armor or defense) done by this item
     * @param minHit              minimum hit done by this item
     * @param maxHit              maximum hit done by this item
     */
    public WeaponProperties(WorldObjectType type, int id, String name, int graphic,
                            int value, int manufactureDifficulty,
                            List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie,
                            boolean noLog, boolean falls, boolean respawnable,
                            int equippedGraphic, boolean stabs, int piercingDamage, int minHit, int maxHit) {
        super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);
        this.stabs = stabs;
        this.piercingDamage = piercingDamage;
        this.minHit = minHit;
        this.maxHit = maxHit;
    }

    public boolean getStabs() {
        return stabs;
    }

    public int getPiercingDamage() {
        return piercingDamage;
    }

    public int getMinHit() {
        return minHit;
    }

    public int getMaxHit() {
        return maxHit;
    }

}
