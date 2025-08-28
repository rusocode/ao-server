package com.ao.model.inventory;

import com.ao.model.worldobject.Item;

/**
 * Character inventory.
 */

public interface Inventory extends Iterable<Item> {

    boolean hasFreeSlots();

    int addItem(Item item);

    Item removeItem(int slot);

    /**
     * Removes the item from the inventory.
     *
     * @param slot   references to the slot of the item to be removed
     * @param amount to the amount of the item to be removed
     * @return the item removed with the amount removed
     */
    Item removeItem(int slot, int amount);

    /**
     * Removes the item from the inventory. If the requested amount is greater than the existing amount, everything is removed.
     *
     * @param item references to the item to be removed
     * @return the item removed, null if the item wasn't in the inventory
     */
    Item removeItem(Item item);

    Item getItem(int slot);

    /**
     * Checks if an Item is in the inventory.
     *
     * @param item references to the item to find
     * @return the slot of the item in the inventory or -1 if the item isn't in the inventory
     */
    int hasItem(Item item);

    int getCapacity();

    void setCapacity(int capacity);

    int getItemAmount(Item item);

    /**
     * Removes all items with an amount of 0.
     */
    void cleanup();

}
