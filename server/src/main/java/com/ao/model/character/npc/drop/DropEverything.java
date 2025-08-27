package com.ao.model.character.npc.drop;

import com.ao.model.character.npc.Drop;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.Item;
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;

import java.util.LinkedList;
import java.util.List;

/**
 * A Drop strategy that simply drops everything the NPC holds.
 */

public class DropEverything implements Drop {

    protected Inventory inventory;

    /**
     * Create a new DropEverything instance.
     *
     * @param inventory an inventory of items to be dropped
     */
    public DropEverything(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public List<WorldObject> getDrops() throws WorldObjectFactoryException {
        List<WorldObject> items = new LinkedList<>();
        for (Item item : inventory)
            if (item != null) items.add(item);
        return items;
    }

}
