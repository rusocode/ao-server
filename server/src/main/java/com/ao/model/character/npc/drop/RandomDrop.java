package com.ao.model.character.npc.drop;

import com.ao.context.ApplicationContext;
import com.ao.model.character.npc.Drop;
import com.ao.model.worldobject.Object;
import com.ao.model.worldobject.factory.ObjectFactoryException;
import com.ao.service.ObjectService;

import java.util.LinkedList;
import java.util.List;

/**
 * Randomly drops any (or none) items from all possible candidates.
 */

public class RandomDrop implements Drop {

    private final ObjectService woService = ApplicationContext.getInstance(ObjectService.class);

    protected List<Dropable> dropables;

    /**
     * Creates a new RandomDrop instance.
     *
     * @param dropables A set of dropables from which to choose. The sum of the chances of dropables must be <= 1.0.
     */
    public RandomDrop(List<Dropable> dropables) {
        this.dropables = dropables;
    }

    @Override
    public List<Object> getDrops() throws ObjectFactoryException {
        List<Object> items = new LinkedList<>();
        double chance = Math.random();
        for (Dropable dropable : dropables) {
            if (dropable.chance < chance) {
                Object item = woService.createObject(dropable.objId, dropable.amount);
                items.add(item);
                break;
            }
            // Decrease and move on
            chance -= dropable.chance;
        }
        return items;
    }

}
