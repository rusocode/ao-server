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

public class DrinkTest extends AbstractItemTest {

    private static final int MIN_THIRST = 1;
    private static final int MAX_THIRST = 5;

    private Drink drink1;
    private Drink drink2;

    @Before
    public void setUp() throws Exception {
        final StatModifyingItemProperties props1 = new StatModifyingItemProperties(WorldObjectType.FOOD, 1, "Apple Juice", 1, 1, null, null, false, false, false, false, MIN_THIRST, MAX_THIRST);
        drink1 = new Drink(props1, 5);
        final StatModifyingItemProperties props2 = new StatModifyingItemProperties(WorldObjectType.FOOD, 1, "Green Apple Juice", 1, 1, null, null, false, false, false, false, MAX_THIRST, MAX_THIRST);
        drink2 = new Drink(props2, 1);
        object = drink2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        drink2.use(character);

        // Consumption of drink2 requires these 2 calls
        verify(inventory).cleanup();
        verify(character).addToThirstiness(MAX_THIRST);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        drink1.use(character);

        // Consumption of drink1 requires just a call to addToThirstiness
        final ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);

        /// Make sure the value is in the correct range
        verify(character).addToThirstiness(capture.capture());
        assertThat(capture.getValue(), greaterThanOrEqualTo(MIN_THIRST));
        assertThat(capture.getValue(), lessThanOrEqualTo(MAX_THIRST));
    }

    @Test
    public void testGetMinThirst() {
        assertEquals(MIN_THIRST, drink1.getMinThirst());
        assertEquals(MAX_THIRST, drink2.getMinThirst());
    }

    @Test
    public void testGetMaxThirst() {
        assertEquals(MAX_THIRST, drink1.getMaxThirst());
        assertEquals(MAX_THIRST, drink2.getMaxThirst());
    }

    @Test
    public void testClone() {
        final Drink clone = (Drink) drink1.clone();

        // Make sure all fields match
        assertEquals(drink1.amount, clone.amount);
        assertEquals(drink1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(drink1, clone);

        final Drink clone2 = (Drink) drink2.clone();

        // Make sure all fields match
        assertEquals(drink2.amount, clone2.amount);
        assertEquals(drink2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(drink2, clone2);
    }

}
