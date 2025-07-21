package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DoorProperties;
import com.ao.model.worldobject.properties.WorldObjectProperties;

public class Door extends AbstractWorldObject {

	public Door(WorldObjectProperties properties) {
		super(properties);
	}

	/**
	 * @return the open.
	 */
	public boolean getOpen() {
		return ((DoorProperties) properties).getOpen();
	}

	/**
	 * @return the locked.
	 */
	public boolean getLocked() {
		return ((DoorProperties) properties).getLocked();
	}

	/**
	 * @return the code.
	 */
	public int getCode() {
		return ((DoorProperties) properties).getCode();
	}
}
