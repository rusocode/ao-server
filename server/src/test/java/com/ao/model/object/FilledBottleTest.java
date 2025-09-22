package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.object.properties.RefillableStatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FilledBottleTest extends AbstractItemTest {

    private static final int THIRST = 5;

    private FilledBottle bottle1;
    private FilledBottle bottle2;


    @BeforeEach
    public void setUp() throws Exception {
        RefillableStatModifyingItemProperties emptyProps = new RefillableStatModifyingItemProperties(ObjectType.EMPTY_BOTTLE, 1, "Water Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
        RefillableStatModifyingItemProperties props = new RefillableStatModifyingItemProperties(ObjectType.FILLED_BOTTLE, 1, "Water Bottle", 1, 1, null, null, false, false, false, false, THIRST, THIRST, true, emptyProps);
        bottle1 = new FilledBottle(props, 5);

        bottle2 = new FilledBottle(props, 1);

        object = bottle2;
        ammount = 1;
        objectProps = props;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        ArgumentCaptor<Item> addedItem = ArgumentCaptor.forClass(Item.class);
        when(inventory.addItem(addedItem.capture())).thenReturn(1);

        bottle2.use(character);

        // Consumption of bottle2 requires these 2 calls
        verify(inventory).cleanup();
        verify(character).addToThirstiness(THIRST);

        assertThat(addedItem.getValue()).isInstanceOf(EmptyBottle.class);
        EmptyBottle emptyBottle = (EmptyBottle) addedItem.getValue();
        assertThat(emptyBottle.properties).isEqualTo(((RefillableStatModifyingItemProperties) bottle2.properties).getOtherStateProperties());
        assertThat(emptyBottle.amount).isEqualTo(1);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        ArgumentCaptor<Item> addedItem = ArgumentCaptor.forClass(Item.class);
        when(inventory.addItem(addedItem.capture())).thenReturn(1);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        bottle1.use(character);

        // Consumption of bottle1 requires just a call to addToThirstiness
        verify(character).addToThirstiness(THIRST);

        assertThat(addedItem.getValue()).isInstanceOf(EmptyBottle.class);
        EmptyBottle emptyBottle = (EmptyBottle) addedItem.getValue();
        assertThat(emptyBottle.properties).isEqualTo(((RefillableStatModifyingItemProperties) bottle1.properties).getOtherStateProperties());
        assertThat(emptyBottle.amount).isEqualTo(1);
    }

    @Test
    public void testClone() {
        FilledBottle clone = (FilledBottle) bottle1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(bottle1.amount);
        assertThat(clone.properties).isEqualTo(bottle1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(bottle1);

        FilledBottle clone2 = (FilledBottle) bottle2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(bottle2.amount);
        assertThat(clone2.properties).isEqualTo(bottle2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(bottle2);
    }

    @Test
    public void testGetThirst() {
        assertThat(bottle1.getThirst()).isEqualTo(THIRST);
        assertThat(bottle2.getThirst()).isEqualTo(THIRST);
    }

}
