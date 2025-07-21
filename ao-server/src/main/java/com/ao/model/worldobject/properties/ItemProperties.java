package com.ao.model.worldobject.properties;

import java.util.List;

import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.WorldObjectType;

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
	 */
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

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the newbie
	 */
	public boolean isNewbie() {
		return newbie;
	}

	/**
	 * @return the forbiddenArchetypes
	 */
	public List<UserArchetype> getForbiddenArchetypes() {
		return forbiddenArchetypes;
	}

	/**
	 * @return the forbiddenRaces
	 */
	public List<Race> getForbiddenRaces() {
		return forbiddenRaces;
	}
	
	/**
	 * @return the noLog.
	 */
	public boolean isNoLog() {
		return noLog;
	}
	
	/**
	 * @return the falls.
	 */
	public boolean isFalls() {
		return falls;
	}
	
	/**
	 * @return the respawn.
	 */
	public boolean isRespawnable() {
		return respawnable;
	}
}
