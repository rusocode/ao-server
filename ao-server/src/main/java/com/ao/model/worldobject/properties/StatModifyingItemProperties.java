package com.ao.model.worldobject.properties;

import java.util.List;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines an Item that modifies a user's stats's properties. Allows a lightweight pattern implementation.
 */
public class StatModifyingItemProperties extends ItemProperties {

	protected int minModifier;
	protected int maxModifier;
	
	/**
	 * Creates a new StatModifyingItemProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param tradeable True if it's tradeable, false otherwise.
	 * @param value The item's value.
	 * @param forbiddenArchetypes List of UserArchetypes not allowed to use this item.
	 * @param forbiddenRaces List of Races not allowed to use this item.
	 * @param newbie Whether the item is newbie or not.
	 * @param noLog Whether this item should be logged or not.
	 * @param falls Whether this item falls or not.
	 * @param respawnable Whether this item respawns or not when in a merchant NPC's inventory.
	 * @param minModifier The minimum amount by which the stats is to be modified.
	 * @param maxModifier The maximum amount by which the stats is to be modified.
	 */
	public StatModifyingItemProperties(WorldObjectType type, int id, String name, int graphic,
			int value, List<UserArchetype> forbiddenArchetypes,
			List<Race> forbiddenRaces, boolean newbie,
			boolean noLog, boolean falls, boolean respawnable, int minModifier, int maxModifier) {
		super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable);
		
		this.minModifier = minModifier;
		this.maxModifier = maxModifier;
	}

	/**
	 * @return the minModifier
	 */
	public int getMinModifier() {
		return minModifier;
	}

	/**
	 * @return the maxModifier
	 */
	public int getMaxModifier() {
		return maxModifier;
	}
}
