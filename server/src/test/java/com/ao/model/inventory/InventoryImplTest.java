package com.ao.model.inventory;

import com.ao.mock.MockFactory;
import com.ao.model.object.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class InventoryImplTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() throws Exception {
        inventory = new InventoryImpl();
    }

    @Test
    public void testAddItem() {
        // Try to add all the items to the inventory
        Item[] item = new Item[inventory.getCapacity()];
        for (int i = 0; i < inventory.getCapacity(); i++) {
            // Basic item mock
            item[i] = MockFactory.mockItem(i + 1, 1);
            assertThat(inventory.addItem(item[i])).isEqualTo(0);
        }

        // TODO Split this test into 3/4

        // Try to add an item when inventory is full and item not repeated
        Item newItem = mock(Item.class);
        when(newItem.getAmount()).thenReturn(1);
        assertThat(inventory.addItem(newItem)).isEqualTo(1);

        // Try to add an item that is repeated when inv. Is full
        when(newItem.getId()).thenReturn(2);
        assertThat(inventory.addItem(newItem)).isEqualTo(0);
        assertThat(inventory.getItemAmount(newItem)).isEqualTo(2);

        // Try to add an item repeated when inventory isn't full but item not exceeds the limit
        inventory.removeItem(0);

        assertThat(inventory.addItem(newItem)).isEqualTo(0);
        assertThat(inventory.getItemAmount(newItem)).isEqualTo(3);

        // Try to add an item repeated when inventory isn't full and the item amount exceeds the limit.
        inventory.removeItem(0);
        inventory.addItem(item[0]);

        when(newItem.getAmount()).thenReturn(9998);
        assertThat(inventory.addItem(newItem)).isGreaterThan(0);
        verify(newItem).addAmount(-9997);
    }

    @Test
    public void testGetItem() {
        Item item = mock(Item.class);

        inventory.addItem(item);
        int slot = inventory.hasItem(item);
        assertThat(inventory.getItem(slot)).isNotNull();

        // Test bounds
        inventory.getItem(-1);
        inventory.getItem(inventory.getCapacity());
    }

    @Test
    public void testHasFreeSlots() {
        Item[] item = new Item[inventory.getCapacity()];
        for (int i = 0; i < inventory.getCapacity(); i++) {
            item[i] = MockFactory.mockItem(i + 1, 1);
            assertThat(inventory.hasFreeSlots()).isTrue();
            inventory.addItem(item[i]);
        }
        assertThat(inventory.hasFreeSlots()).isFalse();
        inventory.removeItem(0);
        assertThat(inventory.hasFreeSlots()).isTrue();
    }

    @Test
    public void testHasItem() {
        Item item = mock(Item.class);
        assertThat(inventory.hasItem(item)).isEqualTo(-1);
        inventory.addItem(item);
        assertThat(inventory.hasItem(item) != -1).isTrue();
    }

    @Test
    public void testRemoveItemInt() {
        Item item = mock(Item.class);
        inventory.addItem(item);
        int slot = inventory.hasItem(item);
        assertThat(inventory.removeItem(slot)).isNotNull();
        assertThat(inventory.hasItem(item)).isEqualTo(-1);
        // Test bounds
        assertThat(inventory.removeItem(-1)).isNull();
        assertThat(inventory.removeItem(inventory.getCapacity())).isNull();
    }

    @Test
    public void testRemoveItemItem() {
        Item item = mock(Item.class);
        Item item2 = mock(Item.class);
        Item itemRemoved = mock(Item.class);

        when(item.getId()).thenReturn(1);
        when(item2.getId()).thenReturn(2);
        when(itemRemoved.getId()).thenReturn(3);

        // Completely remove an item from inventory
        inventory.addItem(item);
        inventory.removeItem(item);
        assertThat(inventory.hasItem(item)).isEqualTo(-1);

        // Remove an item, by more than can be removed
        inventory.addItem(item2);
        inventory.removeItem(itemRemoved);
        assertThat(inventory.hasItem(item2)).isNotEqualTo(-1);

        // Try to check an item not in inventory
        assertThat(inventory.hasItem(itemRemoved)).isEqualTo(-1);

        // item2 IS in inventory (added above), it should return to its position
        assertThat(inventory.hasItem(item2)).isNotEqualTo(-1);
    }

    @Test
    public void testRemoveItemIntInt() {
        Item item = MockFactory.mockItem(1, 2);

        inventory.addItem(item);
        int slot = inventory.hasItem(item);

        Item removedItem = inventory.removeItem(slot, 1);
        assertThat(removedItem).isNotNull();
        assertThat(inventory.hasItem(item)).isNotEqualTo(-1);

        Item removedItem2 = inventory.removeItem(slot, 1);
        assertThat(removedItem2).isNotNull();
        assertThat(inventory.hasItem(item)).isEqualTo(-1);

        // Test bounds
        assertThat(inventory.removeItem(-1, 1)).isNull();
        assertThat(inventory.removeItem(inventory.getCapacity(), 1)).isNull();
    }

    @Test
    public void testGetItemAmount() {
        Item item = MockFactory.mockItem(1, 1);
        Item item2 = MockFactory.mockItem(1, 1000);

        inventory.addItem(item);
        assertThat(inventory.getItemAmount(item)).isEqualTo(1);

        // When adding the second item, the amount should stack up
        inventory.addItem(item2);
        assertThat(inventory.getItemAmount(item)).isEqualTo(1001);
    }

    @Test
    public void testSetCapacity() {
        Item item = mock(Item.class);

        inventory.addItem(item);
        inventory.setCapacity(1);

        assertThat(inventory.getCapacity()).isEqualTo(1);
        assertThat(inventory.getItem(0)).isEqualTo(item);

        // TODO Test when capacity is trimmed and items are droped
    }

    @Test
    public void testCleanup() {
        Item item = MockFactory.mockItem(1, 0);
        Item item2 = MockFactory.mockItem(2, 1);

        inventory.addItem(item);
        inventory.addItem(item2);

        inventory.cleanup();

        assertThat(inventory.hasItem(item)).isEqualTo(-1);
        assertThat(inventory.hasItem(item2)).isNotEqualTo(-1);
    }

}
