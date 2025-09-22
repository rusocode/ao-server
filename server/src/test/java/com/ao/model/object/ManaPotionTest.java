package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.object.properties.StatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ManaPotionTest extends AbstractItemTest {

    private static final int MIN_MANA = 1;
    private static final int MAX_MANA = 5;

    private ManaPotion potion1;
    private ManaPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        StatModifyingItemProperties props1 = new StatModifyingItemProperties(ObjectType.MANA_POTION, 1, "Blue Potion", 1, 1, null, null, false, false, false, false, MIN_MANA, MAX_MANA);
        potion1 = new ManaPotion(props1, 5);

        StatModifyingItemProperties props2 = new StatModifyingItemProperties(ObjectType.MANA_POTION, 1, "Big Blue Potion", 1, 1, null, null, false, false, false, false, MAX_MANA, MAX_MANA);
        potion2 = new ManaPotion(props2, 1);

        object = potion2;
        ammount = 5;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToMana(MAX_MANA);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToMana.
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToMana(capture.capture());

        /// Make sure the value is in the correct range
        assertThat(capture.getValue()).isBetween(MIN_MANA, MAX_MANA);
    }

    @Test
    public void testGetMinMana() {
        assertThat(potion1.getMinMana()).isEqualTo(MIN_MANA);
        assertThat(potion2.getMinMana()).isEqualTo(MAX_MANA);
    }

    @Test
    public void testGetMaxMana() {
        assertThat(potion1.getMaxMana()).isEqualTo(MAX_MANA);
        assertThat(potion2.getMaxMana()).isEqualTo(MAX_MANA);
    }

    @Test
    public void testClone() {
        ManaPotion clone = (ManaPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);

        ManaPotion clone2 = (ManaPotion) potion2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(potion2.amount);
        assertThat(clone2.properties).isEqualTo(potion2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(potion2);
    }

}
