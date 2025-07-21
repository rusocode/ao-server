package com.ao.model.worldobject.properties;

import java.util.List;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Defensive Item's properties. Allows a lightweight pattern implementation.
 */
public class DefensiveItemProperties extends EquippableItemProperties {

	protected int minDef;
	protected int maxDef;

	protected int minMagicDef;
	protected int maxMagicDef;
	
	/**
	 * Creates a new DefensiveItemProperties instance.
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
	 * @param equippedGraphic The id of the grpahic to display when equipped.
	 * @param minDef The minimum defense granted by this item.
	 * @param maxDef The maximum defense granted by this item.
	 * @param minMagicDef The minimum magic defense granted by this item.
	 * @param maxMagicDef The maximum magic defense granted by this item.
	 */
	public DefensiveItemProperties(WorldObjectType type, int id, String name, int graphic,
			int value,	int manufactureDifficulty,
			List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie,
			boolean noLog, boolean falls, boolean respawnable, 
			int equippedGraphic, int minDef, int maxDef, int minMagicDef, int maxMagicDef) {
		super(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic);

		this.minDef = minDef;
		this.maxDef = maxDef;
		
		this.minMagicDef = minMagicDef;
		this.maxMagicDef = maxMagicDef;
	}

	/**
	 * @return the minDef
	 */
	public int getMinDef() {
		return minDef;
	}

	/**
	 * @return the maxDef
	 */
	public int getMaxDef() {
		return maxDef;
	}

	/**
	 * @return the minMagicDef
	 */
	public int getMinMagicDef() {
		return minMagicDef;
	}

	/**
	 * @return the maxMagicDef
	 */
	public int getMaxMagicDef() {
		return maxMagicDef;
	}
}
