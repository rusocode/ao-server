package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.TemporalStatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DexterityPotionTest extends AbstractItemTest {

    private static final int MIN_AGI = 1;
    private static final int MAX_AGI = 5;
    private static final int DURATION = 300;

    private DexterityPotion potion1;
    private DexterityPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        TemporalStatModifyingItemProperties props1 = new TemporalStatModifyingItemProperties(ObjectType.POISON_POTION, 1, "Yellow Potion", 1, 1, null, null, false, false, false, false, MIN_AGI, MAX_AGI, DURATION);
        potion1 = new DexterityPotion(props1, 5);

        TemporalStatModifyingItemProperties props2 = new TemporalStatModifyingItemProperties(ObjectType.POISON_POTION, 1, "Big Yellow Potion", 1, 1, null, null, false, false, false, false, MAX_AGI, MAX_AGI, DURATION);
        potion2 = new DexterityPotion(props2, 1);

        object = potion2;
        ammount = 1;
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
        verify(character).addToDexterity(MAX_AGI, DURATION);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        /// Make sure the value is in the correct range
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToDexterity(capture.capture(), eq(DURATION));
        assertThat(capture.getValue()).isBetween(MIN_AGI, MAX_AGI);
    }

    @Test
    public void testGetMinModifier() {
        assertThat(potion1.getMinModifier()).isEqualTo(MIN_AGI);
        assertThat(potion2.getMinModifier()).isEqualTo(MAX_AGI);
    }

    @Test
    public void testGetMaxModifier() {
        assertThat(potion1.getMaxModifier()).isEqualTo(MAX_AGI);
        assertThat(potion2.getMaxModifier()).isEqualTo(MAX_AGI);
    }

    @Test
    public void testClone() {
        DexterityPotion clone = (DexterityPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);

        DexterityPotion clone2 = (DexterityPotion) potion2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(potion2.amount);
        assertThat(clone2.properties).isEqualTo(potion2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(potion2);
    }

    @Test
    public void testGetEffectDuration() {
        assertThat(potion1.getEffectDuration()).isEqualTo(DURATION);
        assertThat(potion2.getEffectDuration()).isEqualTo(DURATION);
    }

}
