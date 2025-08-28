package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.RefillableStatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmptyBottleTest extends AbstractItemTest {

    private EmptyBottle bottle1;
    private EmptyBottle bottle2;

    @BeforeEach
    public void setUp() throws Exception {
        RefillableStatModifyingItemProperties props1 = new RefillableStatModifyingItemProperties(WorldObjectType.EMPTY_BOTTLE, 1, "Empty Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
        bottle1 = new EmptyBottle(props1, 1);

        RefillableStatModifyingItemProperties props2 = new RefillableStatModifyingItemProperties(WorldObjectType.EMPTY_BOTTLE, 1, "Empty Bottle", 1, 1, null, null, false, false, false, false, 0, 0, false, null);
        bottle2 = new EmptyBottle(props2, 1);

        object = bottle2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUse() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        bottle1.use(character);
        bottle2.use(character);

        // Usage of empty bottles does nothing
        verifyNoInteractions(character);
    }

    @Test
    public void testClone() {
        EmptyBottle clone = (EmptyBottle) bottle1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(bottle1.amount);
        assertThat(clone.properties).isEqualTo(bottle1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(bottle1);

        EmptyBottle clone2 = (EmptyBottle) bottle2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(bottle2.amount);
        assertThat(clone2.properties).isEqualTo(bottle2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(bottle2);
    }

}
