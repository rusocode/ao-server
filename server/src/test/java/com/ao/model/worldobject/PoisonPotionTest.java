package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PoisonPotionTest extends AbstractItemTest {

    private PoisonPotion potion1;
    private PoisonPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        ItemProperties props1 = new ItemProperties(ObjectType.POISON_POTION, 1, "Violet Potion", 1, 1, null, null, false, false, false, false);
        potion1 = new PoisonPotion(props1, 5);

        ItemProperties props2 = new ItemProperties(ObjectType.POISON_POTION, 1, "Violet Potion", 1, 1, null, null, false, false, false, false);
        potion2 = new PoisonPotion(props2, 1);

        object = potion2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).setPoisoned(false);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        // Consumption of potion1 requires just a call to setPoisoned.
        verify(character).setPoisoned(false);
    }

    @Test
    public void testClone() {
        PoisonPotion clone = (PoisonPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);


        PoisonPotion clone2 = (PoisonPotion) potion2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(potion2.amount);
        assertThat(clone2.properties).isEqualTo(potion2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(potion2);
    }

}
