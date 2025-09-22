package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.object.properties.StatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DrinkTest extends AbstractItemTest {

    private static final int MIN_THIRST = 1;
    private static final int MAX_THIRST = 5;

    private Drink drink1;
    private Drink drink2;

    @BeforeEach
    public void setUp() throws Exception {
        StatModifyingItemProperties props1 = new StatModifyingItemProperties(ObjectType.FOOD, 1, "Apple Juice", 1, 1, null, null, false, false, false, false, MIN_THIRST, MAX_THIRST);
        drink1 = new Drink(props1, 5);
        StatModifyingItemProperties props2 = new StatModifyingItemProperties(ObjectType.FOOD, 1, "Green Apple Juice", 1, 1, null, null, false, false, false, false, MAX_THIRST, MAX_THIRST);
        drink2 = new Drink(props2, 1);
        object = drink2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        drink2.use(character);

        // Consumption of drink2 requires these 2 calls
        verify(inventory).cleanup();
        verify(character).addToThirstiness(MAX_THIRST);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        drink1.use(character);

        // Consumption of drink1 requires just a call to addToThirstiness
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);

        /// Make sure the value is in the correct range
        verify(character).addToThirstiness(capture.capture());
        assertThat(capture.getValue()).isBetween(MIN_THIRST, MAX_THIRST);
    }

    @Test
    public void testGetMinThirst() {
        assertThat(drink1.getMinThirst()).isEqualTo(MIN_THIRST);
        assertThat(drink2.getMinThirst()).isEqualTo(MAX_THIRST);
    }

    @Test
    public void testGetMaxThirst() {
        assertThat(drink1.getMaxThirst()).isEqualTo(MAX_THIRST);
        assertThat(drink2.getMaxThirst()).isEqualTo(MAX_THIRST);
    }

    @Test
    public void testClone() {
        Drink clone = (Drink) drink1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(drink1.amount);
        assertThat(clone.properties).isEqualTo(drink1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(drink1);

        Drink clone2 = (Drink) drink2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(drink2.amount);
        assertThat(clone2.properties).isEqualTo(drink2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(drink2);
    }

}
