package com.ao.model.inventory;

import com.ao.model.worldobject.Item;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Concrete implementation of the user inventory.
 */

public class InventoryImpl implements Inventory {

    private static final int DEFAULT_INVENTORY_CAPACITY = 20;

    protected Item[] inventory;

    public InventoryImpl() {
        this(DEFAULT_INVENTORY_CAPACITY);
    }

    public InventoryImpl(int slots) {
        inventory = new Item[slots];
    }

    public InventoryImpl(Item[] inventory) {
        this.inventory = inventory;
    }

    @Override
    public int addItem(final Item item) {
        int i;
        if ((i = hasItem(item)) != -1) {
            int amount = item.getAmount();
            int newAmount, oldAmount;
            int id = item.getId();
            // Stack the item to previous slots
            for (; i < inventory.length; i++) {
                if (inventory[i] != null && inventory[i].getId() == id) {
                    oldAmount = inventory[i].getAmount();
                    newAmount = inventory[i].addAmount(amount);
                    amount += oldAmount - newAmount;
                    if (amount == 0) return 0;
                }
            }
            // Set the item's amount to the remainder
            item.addAmount(amount - item.getAmount());
        }

        for (i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return 0;
            }
        }

        return item.getAmount();
    }

    @Override
    public Item getItem(int slot) {
        if (slot < 0 || slot >= inventory.length) return null;
        return inventory[slot];
    }

    @Override
    public boolean hasFreeSlots() {
        for (Item item : inventory)
            if (item == null) return true;
        return false;
    }

    @Override
    public int hasItem(Item item) {
        int id = item.getId();
        for (int i = 0; i < inventory.length; i++)
            if (inventory[i] != null && inventory[i].getId() == id) return i;
        return -1;
    }

    @Override
    public Item removeItem(int slot) {
        Item item = getItem(slot);
        if (item != null) inventory[slot] = null;
        return item;
    }

    @Override
    public Item removeItem(Item item) {
        int i;
        if ((i = hasItem(item)) == -1) return null;
        Item itemRemoved = item.clone();
        int remainingAmount = item.getAmount();
        int left;
        int id = item.getId();
        for (; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getId() == id) {
                remainingAmount -= inventory[i].getAmount();
                left = inventory[i].addAmount(-(inventory[i].getAmount() + remainingAmount));
                if (left == 0) inventory[i] = null;
                if (remainingAmount <= 0) return itemRemoved;
            }
        }
        // Couldn't remove the full amount, set the proper amount
        itemRemoved.addAmount(-remainingAmount);
        return itemRemoved;
    }

    @Override
    public Item removeItem(int slot, int amount) {
        Item item = getItem(slot);
        if (item == null) return null;
        Item itemRemoved = item.clone();
        int left = item.addAmount(-amount);
        if (left <= 0) {
            inventory[slot] = null;
            left = 0;
        }
        itemRemoved.addAmount(-left);
        return itemRemoved;
    }

    @Override
    public int getCapacity() {
        return inventory.length;
    }

    @Override
    public void setCapacity(int capacity) {
        Item[] tmpInventory = new Item[capacity];
        for (int i = 0; i < capacity; i++)
            tmpInventory[i] = inventory[i];
        // TODO Throw all other items. What happens to non-falling items in such slots?
        inventory = tmpInventory;
    }

    @Override
    public int getItemAmount(Item item) {
        int amount = 0;
        int id = item.getId();
        for (Item i : inventory)
            if (i != null && id == i.getId()) amount += i.getAmount();
        return amount;
    }

    @Override
    public void cleanup() {
        for (int i = inventory.length - 1; i >= 0; i--)
            if (inventory[i] != null && inventory[i].getAmount() == 0) inventory[i] = null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private int currentSlot = 0;

            @Override
            public boolean hasNext() {
                return currentSlot < inventory.length;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return inventory[currentSlot++];
            }

        };
    }

}
