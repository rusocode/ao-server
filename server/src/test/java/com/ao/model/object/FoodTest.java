package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.object.properties.StatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FoodTest extends AbstractItemTest {

    private static final int MIN_HUN = 1;
    private static final int MAX_HUN = 5;

    private Food food1;
    private Food food2;

    @BeforeEach
    public void setUp() throws Exception {
        StatModifyingItemProperties props1 = new StatModifyingItemProperties(ObjectType.FOOD, 1, "Apple", 1, 1, null, null, false, false, false, false, MIN_HUN, MAX_HUN);
        food1 = new Food(props1, 5);

        StatModifyingItemProperties props2 = new StatModifyingItemProperties(ObjectType.FOOD, 1, "Green Apple", 1, 1, null, null, false, false, false, false, MAX_HUN, MAX_HUN);
        food2 = new Food(props2, 1);

        object = food2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        food2.use(character);

        // Consumption of food2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToHunger(MAX_HUN);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        food1.use(character);

        // Consumption of food1 requires just a call to addToHunger.
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToHunger(capture.capture());

        /// Make sure the value is in the correct range
        assertThat(capture.getValue()).isBetween(MIN_HUN, MAX_HUN);
    }

    @Test
    public void testGetMinHun() {
        assertThat(food1.getMinHun()).isEqualTo(MIN_HUN);
        assertThat(food2.getMinHun()).isEqualTo(MAX_HUN);
    }

    @Test
    public void testGetMaxHun() {
        assertThat(food1.getMaxHun()).isEqualTo(MAX_HUN);
        assertThat(food2.getMaxHun()).isEqualTo(MAX_HUN);
    }

    @Test
    public void testClone() {
        Food clone = (Food) food1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(food1.amount);
        assertThat(clone.properties).isEqualTo(food1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(food1);

        Food clone2 = (Food) food2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(food2.amount);
        assertThat(clone2.properties).isEqualTo(food2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(food2);
    }

}
