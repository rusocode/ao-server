package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FoodTest extends AbstractItemTest {

    private static final int MIN_HUN = 1;
    private static final int MAX_HUN = 5;

    private Food food1;
    private Food food2;

    @Before
    public void setUp() throws Exception {
        final StatModifyingItemProperties props1 = new StatModifyingItemProperties(WorldObjectType.FOOD, 1, "Apple", 1, 1, null, null, false, false, false, false, MIN_HUN, MAX_HUN);
        food1 = new Food(props1, 5);

        final StatModifyingItemProperties props2 = new StatModifyingItemProperties(WorldObjectType.FOOD, 1, "Green Apple", 1, 1, null, null, false, false, false, false, MAX_HUN, MAX_HUN);
        food2 = new Food(props2, 1);

        object = food2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        food2.use(character);

        // Consumption of food2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToHunger(MAX_HUN);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        food1.use(character);

        // Consumption of food1 requires just a call to addToHunger.
        final ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToHunger(capture.capture());

        /// Make sure the value is in the correct range
        assertThat(capture.getValue(), greaterThanOrEqualTo(MIN_HUN));
        assertThat(capture.getValue(), lessThanOrEqualTo(MAX_HUN));
    }

    @Test
    public void testGetMinHun() {
        assertEquals(MIN_HUN, food1.getMinHun());
        assertEquals(MAX_HUN, food2.getMinHun());
    }

    @Test
    public void testGetMaxHun() {
        assertEquals(MAX_HUN, food1.getMaxHun());
        assertEquals(MAX_HUN, food2.getMaxHun());
    }

    @Test
    public void testClone() {
        final Food clone = (Food) food1.clone();

        // Make sure all fields match
        assertEquals(food1.amount, clone.amount);
        assertEquals(food1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(food1, clone);


        final Food clone2 = (Food) food2.clone();

        // Make sure all fields match
        assertEquals(food2.amount, clone2.amount);
        assertEquals(food2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(food2, clone2);
    }

}
