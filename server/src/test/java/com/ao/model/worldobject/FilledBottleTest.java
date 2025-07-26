package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.RefillableStatModifyingItemProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FilledBottleTest extends AbstractItemTest {

    private static final int THIRST = 5;

    private FilledBottle bottle1;
    private FilledBottle bottle2;


    @Before
    public void setUp() throws Exception {
        final RefillableStatModifyingItemProperties emptyProps = new RefillableStatModifyingItemProperties(WorldObjectType.EMPTY_BOTTLE, 1, "Water Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
        final RefillableStatModifyingItemProperties props = new RefillableStatModifyingItemProperties(WorldObjectType.FILLED_BOTTLE, 1, "Water Bottle", 1, 1, null, null, false, false, false, false, THIRST, THIRST, true, emptyProps);
        bottle1 = new FilledBottle(props, 5);

        bottle2 = new FilledBottle(props, 1);

        object = bottle2;
        ammount = 1;
        objectProps = props;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        final ArgumentCaptor<Item> addedItem = ArgumentCaptor.forClass(Item.class);
        when(inventory.addItem(addedItem.capture())).thenReturn(1);

        bottle2.use(character);

        // Consumption of bottle2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToThirstiness(THIRST);

        assertThat(addedItem.getValue(), instanceOf(EmptyBottle.class));
        final EmptyBottle emptyBottle = (EmptyBottle) addedItem.getValue();
        assertEquals(((RefillableStatModifyingItemProperties) bottle2.properties).getOtherStateProperties(), emptyBottle.properties);
        assertEquals(1, emptyBottle.amount);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final ArgumentCaptor<Item> addedItem = ArgumentCaptor.forClass(Item.class);
        when(inventory.addItem(addedItem.capture())).thenReturn(1);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        bottle1.use(character);

        // Consumption of bottle1 requires just a call to addToThirstiness.
        verify(character).addToThirstiness(THIRST);

        assertThat(addedItem.getValue(), instanceOf(EmptyBottle.class));
        final EmptyBottle emptyBottle = (EmptyBottle) addedItem.getValue();
        assertEquals(((RefillableStatModifyingItemProperties) bottle1.properties).getOtherStateProperties(), emptyBottle.properties);
        assertEquals(1, emptyBottle.amount);
    }

    @Test
    public void testClone() {
        final FilledBottle clone = (FilledBottle) bottle1.clone();

        // Make sure all fields match
        assertEquals(bottle1.amount, clone.amount);
        assertEquals(bottle1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(bottle1, clone);


        final FilledBottle clone2 = (FilledBottle) bottle2.clone();

        // Make sure all fields match
        assertEquals(bottle2.amount, clone2.amount);
        assertEquals(bottle2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(bottle2, clone2);
    }

    @Test
    public void testGetThirst() {
        assertEquals(THIRST, bottle1.getThirst());
        assertEquals(THIRST, bottle2.getThirst());
    }

}
