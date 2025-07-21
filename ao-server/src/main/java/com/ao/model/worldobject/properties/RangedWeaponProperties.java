package com.ao.model.worldobject.properties;

import java.util.List;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Ranged Weapon's properties. Allows a lightweight pattern implementation.
 */
public class RangedWeaponProperties extends WeaponProperties {

	protected boolean needsAmmunition;
	
	/**
	 * Creates a new RangedWeaponProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param tradeable True if it's tradeable, false otherwise.
	 * @param value The item's value.
	 * @param manufactureDifficulty The item's manufacture difficulty.
	 * @param forbiddenArchetypes List of UserArchetypes not allowed to use this item.
	 * @param forbiddenRaces List of Races not allowed to use this item.
	 * @param newbie Whether the item is newbie or not.
	 * @param noLog Whether this item should be logged or not.
	 * @param falls Whether this item falls or not.
	 * @param respawnable Whether this item respawns or not when in a merchant NPC's inventory.
	 * @param equippedGraphic The id of the grpahic to display when equipped.
	 * @param stabs Wether if this item stabs or not.
	 * @param piercingDamage The pircing damage (not reduced by any kind of armor or defense) done by this item.
	 * @param minHit The minimum hit done by this item.
	 * @param maxHit The maximum hit done by this item.
	 * @param needsAmmunition True if the weapon needs ammunition, false of it's a throwable weapon.
	 */
	public RangedWeaponProperties(WorldObjectType type, int id, String name, int graphic,
			int value,
			int manufactureDifficulty, List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces,
			boolean newbie, boolean noLog, boolean falls, boolean respawnable, int equippedGraphic, boolean stabs,
			int piercingDamage, int minHit, int maxHit, boolean needsAmmunition) {
		super(type, id, name, graphic, value,
				manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic,
				stabs, piercingDamage, minHit, maxHit);
		
		this.needsAmmunition = needsAmmunition;
	}

	/**
	 * @return the needsAmmunition
	 */
	public boolean getNeedsAmmunition() {
		return needsAmmunition;
	}
}
