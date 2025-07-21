package com.ao.model.character.npc.drop;

import java.util.LinkedList;
import java.util.List;

import com.ao.model.character.npc.Drop;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.Item;
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;

/**
 * A Drop strategy that simply drops everything the NPC holds.
 * @author itirabasso
 */
public class DropEverything implements Drop {

	protected Inventory inventory;

	/**
	 * Create a new DropEverything instance.
	 * @param inventory An inventory of items to be dropped.
	 */
	public DropEverything(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public List<WorldObject> getDrops() throws WorldObjectFactoryException {
		List<WorldObject> items = new LinkedList<WorldObject>();

		for (final Item item : inventory) {
			if (item != null) {
				items.add(item);
			}
		}

		return items;
	}
}
