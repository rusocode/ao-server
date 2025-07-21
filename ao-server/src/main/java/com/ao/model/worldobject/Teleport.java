package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.TeleportProperties;

/**
 * A teleport object.
 */
public class Teleport extends AbstractWorldObject {

	/**
	 * Creates a new Teleport instance.
	 * @param properties The object's properties.
	 */
	public Teleport(TeleportProperties properties) {
		super(properties);
	}

	/**
	 * Retrieves the teleport's radius.
	 * @return The teleport's radius.
	 */
	public int getRadius() {
		return ((TeleportProperties) properties).getRadius();
	}
}
