package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a WorldObject's properties. Allows a lightweight pattern implementation.
 */
public class WorldObjectProperties {

	protected int id;
	protected String name;
	protected int graphic;
	protected WorldObjectType type;
	
	/**
	 * Creates a new WorldObjectProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 */
	public WorldObjectProperties(WorldObjectType type, int id, String name, int graphic) {
		this.id = id;
		this.name = name;
		this.graphic = graphic;
		this.type = type;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the graphic
	 */
	public int getGraphic() {
		return graphic;
	}

	/**
	 * @return the type
	 */
	public WorldObjectType getType() {
		return type;
	}
}
