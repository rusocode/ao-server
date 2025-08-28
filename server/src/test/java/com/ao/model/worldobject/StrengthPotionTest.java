package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.TemporalStatModifyingItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class StrengthPotionTest extends AbstractItemTest {

    private static final int MIN_STR = 1;
    private static final int MAX_STR = 5;
    private static final int DURATION = 300;

    private StrengthPotion potion1;
    private StrengthPotion potion2;

    @BeforeEach
    public void setUp() throws Exception {
        TemporalStatModifyingItemProperties props1 = new TemporalStatModifyingItemProperties(WorldObjectType.STRENGTH_POTION, 1, "Green Potion", 1, 1, null, null, false, false, false, false, MIN_STR, MAX_STR, DURATION);
        potion1 = new StrengthPotion(props1, 5);

        TemporalStatModifyingItemProperties props2 = new TemporalStatModifyingItemProperties(WorldObjectType.STRENGTH_POTION, 1, "Big Green Potion", 1, 1, null, null, false, false, false, false, MAX_STR, MAX_STR, DURATION);
        potion2 = new StrengthPotion(props2, 1);

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
        verify(character).addToStrength(MAX_STR, DURATION);
    }

    @Test
    public void testUseWithoutCleanup() {
        Inventory inventory = mock(Inventory.class);
        Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToStrength.
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(character).addToStrength(capture.capture(), eq(DURATION));

        /// Make sure the value is in the correct range
        assertThat(capture.getValue()).isBetween(MIN_STR, MAX_STR);
    }

    @Test
    public void testGetMinModifier() {
        assertThat(potion1.getMinModifier()).isEqualTo(MIN_STR);
        assertThat(potion2.getMinModifier()).isEqualTo(MAX_STR);
    }

    @Test
    public void testGetMaxModifier() {
        assertThat(potion1.getMaxModifier()).isEqualTo(MAX_STR);
        assertThat(potion2.getMaxModifier()).isEqualTo(MAX_STR);
    }

    @Test
    public void testClone() {
        StrengthPotion clone = (StrengthPotion) potion1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(potion1.amount);
        assertThat(clone.properties).isEqualTo(potion1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(potion1);

        StrengthPotion clone2 = (StrengthPotion) potion2.clone();

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
