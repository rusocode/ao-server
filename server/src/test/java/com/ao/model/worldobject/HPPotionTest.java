package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.StatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HPPotionTest extends AbstractItemTest {

    private static final int MIN_HP = 1;
    private static final int MAX_HP = 5;

    private HPPotion potion1;
    private HPPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        final StatModifyingItemProperties props1 = new StatModifyingItemProperties(WorldObjectType.HP_POTION, 1, "Red Potion", 1, 1, null, null, false, false, false, false, MIN_HP, MAX_HP);
        potion1 = new HPPotion(props1, 5);

        final StatModifyingItemProperties props2 = new StatModifyingItemProperties(WorldObjectType.HP_POTION, 1, "Big Red Potion", 1, 1, null, null, false, false, false, false, MAX_HP, MAX_HP);
        potion2 = new HPPotion(props2, 1);

        object = potion2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToHitPoints(MAX_HP);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToHitPoints.
        final ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToHitPoints(capture.capture());
        /// Make sure the value is in the correct range
        assertThat(capture.getValue()).isBetween(MIN_HP, MAX_HP);
    }

    @Test
    public void testGetMinHP() {
        assertThat(potion1.getMinHP()).isEqualTo(MIN_HP);
        assertThat(potion2.getMinHP()).isEqualTo(MAX_HP);
    }

    @Test
    public void testGetMaxHP() {
        assertThat(potion1.getMaxHP()).isEqualTo(MAX_HP);
        assertThat(potion2.getMaxHP()).isEqualTo(MAX_HP);
    }

    @Test
    public void testClone() {
        final HPPotion clone = (HPPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);

        final HPPotion clone2 = (HPPotion) potion2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(potion2.amount);
        assertThat(clone2.properties).isEqualTo(potion2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(potion2);
    }

}
