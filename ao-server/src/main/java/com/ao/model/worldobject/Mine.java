package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;

/**
 * A mine. Does nothing, just sits around.
 */
public class Mine extends AbstractResourceSource {

	/**
	 * Creates a new Mine instance.
	 * @param properties The object's properties.
	 */
	public Mine(ResourceSourceProperties properties) {
		super(properties);
	}
}
