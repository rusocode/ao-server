package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.*;

public class DeathPotionTest extends AbstractItemTest {

    private static final Integer USER_HP = 30;

    private DeathPotion potion1;
    private DeathPotion potion2;

    @Before
    public void setUp() throws Exception {
        final ItemProperties props1 = new ItemProperties(WorldObjectType.DEATH_POTION, 1, "Black Potion", 1, 1, null, null, false, false, false, true);
        potion1 = new DeathPotion(props1, 5);

        final ItemProperties props2 = new ItemProperties(WorldObjectType.DEATH_POTION, 1, "Black Potion", 1, 1, null, null, false, true, true, false);
        potion2 = new DeathPotion(props2, 1);

        object = potion2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUseWithCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);
        when(character.getHitPoints()).thenReturn(USER_HP);

        potion2.use(character);

        // Consumption of potion2 requires these 2 calls.
        verify(inventory).cleanup();
        verify(character).addToHitPoints(-USER_HP);
    }

    @Test
    public void testUseWithoutCleanup() {
        final Inventory inventory = mock(Inventory.class);
        final Character character = mock(Character.class);
        when(character.getInventory()).thenReturn(inventory);
        when(character.getHitPoints()).thenReturn(USER_HP);

        potion1.use(character);

        // Consumption of potion1 requires just a call to addToHitPoints.
        verify(character).addToHitPoints(-USER_HP);
    }

    @Test
    public void testClone() {
        final DeathPotion clone = (DeathPotion) potion1.clone();

        // Make sure all fields match
        assertEquals(potion1.amount, clone.amount);
        assertEquals(potion1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(potion1, clone);


        final DeathPotion clone2 = (DeathPotion) potion2.clone();

        // Make sure all fields match
        assertEquals(potion2.amount, clone2.amount);
        assertEquals(potion2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(potion2, clone2);
    }

}
