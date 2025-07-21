package com.ao.model.character.npc;

import java.util.List;

import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;

/**
 * An interface to cope with the task of choosing what items to drop, if any.
 * @author itirabasso
 */
public interface Drop {

	/**
	 * Retrieve the list of objects to be dropped.
	 * @return a list with the drops.
	 * @throws WorldObjectFactoryException
	 */
	List<WorldObject> getDrops() throws WorldObjectFactoryException;

}
