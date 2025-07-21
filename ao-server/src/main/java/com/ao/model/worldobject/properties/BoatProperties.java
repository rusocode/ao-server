package com.ao.model.worldobject.properties;

import java.util.List;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Boat's properties. Allows a lightweight pattern implementation.
 */
public class BoatProperties extends DefensiveItemProperties {

	protected int minHit;
	protected int maxHit;
	
	protected int usageDifficulty;
	
	/**
	 * Creates a new BoatProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param value The item's value.
	 * @param usageDifficulty The item's usage difficulty.
	 * @param manufactureDifficulty The item's manufacture difficulty.
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
	 * @param minHit The minimum hit granted by this boat.
	 * @param maxHit The maximum hit granted by this boat.
	 */
	public BoatProperties(WorldObjectType type, int id, String name, int graphic, int value, int usageDifficulty, int manufactureDifficulty,
			List<UserArchetype> forbiddenArchetypes, List<Race> forbiddenRaces, boolean newbie,
			boolean noLog, boolean falls, boolean respawnable,
			int equippedGraphic, int minDef, int maxDef, int minMagicDef,
			int maxMagicDef, int minHit, int maxHit) {
		super(type, id, name, graphic, value,
				manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawnable, equippedGraphic,
				minDef, maxDef, minMagicDef, maxMagicDef);
		
		this.minHit = minHit;
		this.maxHit = maxHit;
		
		this.usageDifficulty = usageDifficulty;
	}

	/**
	 * @return the minHit
	 */
	public int getMinHit() {
		return minHit;
	}

	/**
	 * @return the maxHit
	 */
	public int getMaxHit() {
		return maxHit;
	}

	/**
	 * @return the usageDifficulty
	 */
	public int getUsageDifficulty() {
		return usageDifficulty;
	}
}
