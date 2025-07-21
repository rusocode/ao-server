package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a Teleport's properties. Allows a lightweight pattern implementation.
 */
public class TeleportProperties extends WorldObjectProperties {

	protected int radius;
	
	/**
	 * Creates a new TeleportProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param radius The radius of the teleport.
	 */
	public TeleportProperties(WorldObjectType type, int id, String name, int graphic, int radius) {
		super(type, id, name, graphic);
		
		this.radius = radius;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
}
