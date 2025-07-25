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

public class ManaPotionTest extends AbstractItemTest {

    private static final int MIN_MANA = 1;
    private static final int MAX_MANA = 5;

    private ManaPotion potion1;
    private ManaPotion potion2;

    @Before
    public void setUp() throws Exception {
        final StatModifyingItemProperties props1 = new StatModifyingItemProperties(WorldObjectType.MANA_POTION, 1, "Blue Potion", 1, 1, null, null, false, false, false, false, MIN_MANA, MAX_MANA);
        potion1 = new ManaPotion(props1, 5);

        final StatModifyingItemProperties props2 = new StatModifyingItemProperties(WorldObjectType.MANA_POTION, 1, "Big Blue Potion", 1, 1, null, null, false, false, false, false, MAX_MANA, MAX_MANA);
        potion2 = new ManaPotion(props2, 1);

        object = potion2;
        ammount = 5;
        objectProps = props2;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToMana(MAX_MANA);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToMana.
        final ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToMana(capture.capture());

        /// Make sure the value is in the correct range
        assertThat(capture.getValue(), greaterThanOrEqualTo(MIN_MANA));
        assertThat(capture.getValue(), lessThanOrEqualTo(MAX_MANA));
    }

    @Test
    public void testGetMinMana() {
        assertEquals(MIN_MANA, potion1.getMinMana());
        assertEquals(MAX_MANA, potion2.getMinMana());
    }

    @Test
    public void testGetMaxMana() {
        assertEquals(MAX_MANA, potion1.getMaxMana());
        assertEquals(MAX_MANA, potion2.getMaxMana());
    }

    @Test
    public void testClone() {
        final ManaPotion clone = (ManaPotion) potion1.clone();

        // Make sure all fields match
        assertEquals(potion1.amount, clone.amount);
        assertEquals(potion1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(potion1, clone);


        final ManaPotion clone2 = (ManaPotion) potion2.clone();

        // Make sure all fields match
        assertEquals(potion2.amount, clone2.amount);
        assertEquals(potion2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(potion2, clone2);
    }

}
