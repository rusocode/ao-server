package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeathPotionTest extends AbstractItemTest {

    private static final Integer USER_HP = 30;

    private DeathPotion potion1;
    private DeathPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        ItemProperties props1 = new ItemProperties(WorldObjectType.DEATH_POTION, 1, "Black Potion", 1, 1, null, null, false, false, false, true);
        potion1 = new DeathPotion(props1, 5);

        ItemProperties props2 = new ItemProperties(WorldObjectType.DEATH_POTION, 1, "Black Potion", 1, 1, null, null, false, true, true, false);
        potion2 = new DeathPotion(props2, 1);

        object = potion2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);
        when(character.getHitPoints()).thenReturn(USER_HP);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToHitPoints(-USER_HP);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);
        when(character.getHitPoints()).thenReturn(USER_HP);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToHitPoints.
        verify(character).addToHitPoints(-USER_HP);
    }

    @Test
    public void testClone() {
        DeathPotion clone = (DeathPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);


        DeathPotion clone2 = (DeathPotion) potion2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(potion2.amount);
        assertThat(clone2.properties).isEqualTo(potion2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(potion2);
    }

}
