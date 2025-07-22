package com.ao.model.inventory;

import com.ao.mock.MockFactory;
import com.ao.model.worldobject.Item;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InventoryImplTest {

    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        inventory = new InventoryImpl();
    }

    @Test
    public void testAddItem() {
        // Try to add all the items to the inventory
        final Item[] item = new Item[inventory.getCapacity()];
        for (int i = 0; i < inventory.getCapacity(); i++) {
            // Basic item mock
            item[i] = MockFactory.mockItem(i + 1, 1);
            assertEquals(0, inventory.addItem(item[i]));
        }

        // TODO Split this test into 3/4

        // Try to add an item when inventory is full and item not repeated.
        final Item newItem = mock(Item.class);
        when(newItem.getAmount()).thenReturn(1);
        assertEquals(1, inventory.addItem(newItem));

        // Try to add an item that is repeated when inv. Is full
        when(newItem.getId()).thenReturn(2);
        assertEquals(0, inventory.addItem(newItem));
        assertEquals(2, inventory.getItemAmount(newItem));

        // Try to add an item repeated when inventory isn't full but item not exceed the limit
        inventory.removeItem(0);

        assertEquals(0, inventory.addItem(newItem));
        assertEquals(3, inventory.getItemAmount(newItem));

        // Try to add an item that is repeated when inventory isn't full and item amount exceeds the limit.
        inventory.removeItem(0);
        inventory.addItem(item[0]);

        when(newItem.getAmount()).thenReturn(9998);
        assertThat(inventory.addItem(newItem), greaterThan(0));
        verify(newItem).addAmount(-9997);
    }

    @Test
    public void testGetItem() {
        final Item item = mock(Item.class);

        inventory.addItem(item);
        int slot = inventory.hasItem(item);
        assertNotNull(inventory.getItem(slot));

        // Test bounds
        inventory.getItem(-1);
        inventory.getItem(inventory.getCapacity());
    }

    @Test
    public void testHasFreeSlots() {
        final Item[] item = new Item[inventory.getCapacity()];
        for (int i = 0; i < inventory.getCapacity(); i++) {
            item[i] = MockFactory.mockItem(i + 1, 1);
            assertTrue(inventory.hasFreeSlots());
            inventory.addItem(item[i]);
        }
        assertFalse(inventory.hasFreeSlots());
        inventory.removeItem(0);
        assertTrue(inventory.hasFreeSlots());
    }

    @Test
    public void testHasItem() {
        final Item item = mock(Item.class);
        assertEquals(-1, inventory.hasItem(item));
        inventory.addItem(item);
        assertTrue(inventory.hasItem(item) != -1);
    }

    @Test
    public void testRemoveItemInt() {
        final Item item = mock(Item.class);
        inventory.addItem(item);
        int slot = inventory.hasItem(item);
        assertNotNull(inventory.removeItem(slot));
        assertEquals(-1, inventory.hasItem(item));
        // Test bounds
        assertNull(inventory.removeItem(-1));
        assertNull(inventory.removeItem(inventory.getCapacity()));
    }

    @Test
    public void testRemoveItemItem() {
        final Item item = mock(Item.class);
        final Item item2 = mock(Item.class);
        final Item itemRemoved = mock(Item.class);

        when(item.getId()).thenReturn(1);
        when(item2.getId()).thenReturn(2);
        when(itemRemoved.getId()).thenReturn(3);

        // Completely remove an item from inventory
        inventory.addItem(item);
        inventory.removeItem(item);
        assertEquals(-1, inventory.hasItem(item));

        // Remove an item, by more than can be removed
        inventory.addItem(item2);
        inventory.removeItem(itemRemoved);
        assertThat(inventory.hasItem(item2), not(equalTo(-1)));

        // Try to remove an item not in inventory
        assertNull(inventory.removeItem(item));
    }

    @Test
    public void testRemoveItemIntInt() {
        final Item item = MockFactory.mockItem(1, 2);

        inventory.addItem(item);
        final int slot = inventory.hasItem(item);

        final Item removedItem = inventory.removeItem(slot, 1);
        assertNotNull(removedItem);
        assertThat(inventory.hasItem(item), not(equalTo(-1)));

        final Item removedItem2 = inventory.removeItem(slot, 1);
        assertNotNull(removedItem2);
        assertEquals(-1, inventory.hasItem(item));

        // Test bounds
        assertNull(inventory.removeItem(-1, 1));
        assertNull(inventory.removeItem(inventory.getCapacity(), 1));
    }

    @Test
    public void testGetItemAmount() {
        final Item item = MockFactory.mockItem(1, 1);
        final Item item2 = MockFactory.mockItem(1, 1000);

        inventory.addItem(item);
        assertEquals(1, inventory.getItemAmount(item));

        // When adding the second item, amount should stack up
        inventory.addItem(item2);
        assertEquals(1001, inventory.getItemAmount(item));
    }

    @Test
    public void testSetCapacity() {
        final Item item = mock(Item.class);

        inventory.addItem(item);
        inventory.setCapacity(1);

        assertEquals(1, inventory.getCapacity());
        assertEquals(item, inventory.getItem(0));

        // TODO Test when capacity is trimmed and items are droped
    }

    @Test
    public void testCleanup() {
        final Item item = MockFactory.mockItem(1, 0);
        final Item item2 = MockFactory.mockItem(2, 1);

        inventory.addItem(item);
        inventory.addItem(item2);

        inventory.cleanup();

        assertEquals(-1, inventory.hasItem(item));
        assertThat(inventory.hasItem(item2), not(equalTo(-1)));
    }

}
