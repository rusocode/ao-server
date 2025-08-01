package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.TemporalStatModifyingItemProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DexterityPotionTest extends AbstractItemTest {

    private static final int MIN_AGI = 1;
    private static final int MAX_AGI = 5;
    private static final int DURATION = 300;

    private DexterityPotion potion1;
    private DexterityPotion potion2;

    @Before
    public void setUp() throws Exception {
        final TemporalStatModifyingItemProperties props1 = new TemporalStatModifyingItemProperties(WorldObjectType.POISON_POTION, 1, "Yellow Potion", 1, 1, null, null, false, false, false, false, MIN_AGI, MAX_AGI, DURATION);
        potion1 = new DexterityPotion(props1, 5);

        final TemporalStatModifyingItemProperties props2 = new TemporalStatModifyingItemProperties(WorldObjectType.POISON_POTION, 1, "Big Yellow Potion", 1, 1, null, null, false, false, false, false, MAX_AGI, MAX_AGI, DURATION);
        potion2 = new DexterityPotion(props2, 1);

        object = potion2;
        ammount = 1;
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
        verify(character).addToDexterity(MAX_AGI, DURATION);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        /// Make sure the value is in the correct range
        final ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToDexterity(capture.capture(), eq(DURATION));
        assertThat(capture.getValue(), greaterThanOrEqualTo(MIN_AGI));
        assertThat(capture.getValue(), lessThanOrEqualTo(MAX_AGI));
    }

    @Test
    public void testGetMinModifier() {
        assertEquals(MIN_AGI, potion1.getMinModifier());
        assertEquals(MAX_AGI, potion2.getMinModifier());
    }

    @Test
    public void testGetMaxModifier() {
        assertEquals(MAX_AGI, potion1.getMaxModifier());
        assertEquals(MAX_AGI, potion2.getMaxModifier());
    }

    @Test
    public void testClone() {
        final DexterityPotion clone = (DexterityPotion) potion1.clone();

        // Make sure all fields match
        assertEquals(potion1.amount, clone.amount);
        assertEquals(potion1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(potion1, clone);


        final DexterityPotion clone2 = (DexterityPotion) potion2.clone();

        // Make sure all fields match
        assertEquals(potion2.amount, clone2.amount);
        assertEquals(potion2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(potion2, clone2);
    }

    @Test
    public void testGetEffectDuration() {
        assertEquals(DURATION, potion1.getEffectDuration());
        assertEquals(DURATION, potion2.getEffectDuration());
    }
}
