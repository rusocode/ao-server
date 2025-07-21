package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */
public class ForumProperties extends WorldObjectProperties {
	
	protected String forumName;
	
	/**
	 * Creates a new ForumProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param bigGraphic The big graphic for the item.
	 * @param text The text for the item.
	 */
	public ForumProperties(WorldObjectType type, int id, String name, int graphic, String forumName) {
		super(type, id, name, graphic);
		
		this.forumName = forumName;
	}

	/**
	 * @return the forum name.
	 */
	public String getForumName() {
		return forumName;
	}
}
