package com.ao.model.worldobject.properties;

import com.ao.model.worldobject.ResourceSourceType;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a ResourceSourceProperties properties. Allows a lightweight pattern implementation.
 */
public class ResourceSourceProperties extends WorldObjectProperties {

	protected int resourceWorldObjctId;
	protected ResourceSourceType resourceSourceType;
	
	/**
	 * Creates a new ResourceSourceProperties instance.
	 * @param type The type of the item.
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param graphic The graphic for the item.
	 * @param resourceWorldObjctId The id of the world object being produced by this resources source.
	 * @param resourceSourceType the resource source type.
	 */
	public ResourceSourceProperties(WorldObjectType type, int id, String name, int graphic, int resourceWorldObjctId, ResourceSourceType resourceSourceType) {
		super(type, id, name, graphic);
		
		this.resourceWorldObjctId = resourceWorldObjctId;
		this.resourceSourceType = resourceSourceType;
	}

	/**
	 * @return the resourceWorldObjctId
	 */
	public int getResourceWorldObjctId() {
		return resourceWorldObjctId;
	}
	
	/**
	 * @return the resource source type.
	 */
	public ResourceSourceType getResourceSourceType() {
		return resourceSourceType;
	}
}
