package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MineralTest extends AbstractItemTest {

    private Mineral mineral1;
    private Mineral mineral2;

    @BeforeEach
    public void setUp() throws Exception {
        ItemProperties props1 = new ItemProperties(WorldObjectType.MINERAL, 1, "Gold", 1, 1, null, null, false, false, false, false);
        mineral1 = new Mineral(props1, 1);

        ItemProperties props2 = new ItemProperties(WorldObjectType.MINERAL, 1, "Cooper", 1, 1, null, null, false, false, false, false);
        mineral2 = new Mineral(props2, 1);

        object = mineral2;
        ammount = 1;
        objectProps = props2;
    }

    @Test
    public void testUse() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        mineral1.use(character);
        mineral2.use(character);

        // Usage of minerals does nothing
        verifyNoInteractions(character);
    }

    @Test
    public void testClone() {
        Mineral clone = (Mineral) mineral1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(mineral1.amount);
        assertThat(clone.properties).isEqualTo(mineral1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(mineral1);

        Mineral clone2 = (Mineral) mineral2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(mineral2.amount);
        assertThat(clone2.properties).isEqualTo(mineral2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(mineral2);
    }

}
