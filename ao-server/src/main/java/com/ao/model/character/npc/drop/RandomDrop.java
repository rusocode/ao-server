package com.ao.model.character.npc.drop;

import java.util.LinkedList;
import java.util.List;

import com.ao.context.ApplicationContext;
import com.ao.model.character.npc.Drop;
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;
import com.ao.service.WorldObjectService;

/**
 * Randomly drops any (or none) items from all possible candidates.
 * @author jsotuyod
 */
public class RandomDrop implements Drop {

	private final WorldObjectService woService = ApplicationContext.getInstance(WorldObjectService.class);

	protected List<Dropable> dropables;

	/**
	 * Creates a new RandomDrop instance.
	 * @param dropables A set of dropables from which to choose. The sum of the chances of dropables must be <= 1.0
	 */
	public RandomDrop(List<Dropable> dropables) {
		this.dropables = dropables;
	}

	@Override
	public List<WorldObject> getDrops() throws WorldObjectFactoryException {
		List<WorldObject> items = new LinkedList<WorldObject>();

		double chance = Math.random();

		for (Dropable dropable : dropables) {
			if (dropable.chance < chance) {
				WorldObject item = woService.createWorldObject(dropable.objId, dropable.amount);
				items.add(item);
				break;
			}

			// Decrease and move on
			chance -= dropable.chance;
		}

		return items;
	}

}
