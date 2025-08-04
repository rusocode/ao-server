package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.inventory.Inventory;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GoldTest extends AbstractItemTest {

    private Gold gold1;
    private Gold gold2;

    @BeforeEach
    public void setUp() throws Exception {
        final ItemProperties props = new ItemProperties(WorldObjectType.MONEY, 13, "Monedas de oro", 1, 1, null, null, false, true, true, true);
        gold1 = new Gold(props, 1000);
        gold2 = new Gold(props, 2000);

        object = gold1;
        objectProps = props;
        ammount = 1000;
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);
        final Inventory inventory = mock(Inventory.class);
        when(character.getInventory()).thenReturn(inventory);

        gold1.use(character);

        verify(inventory).cleanup();
        verify(character).addMoney(1000);
    }

    @Test
    public void testClone() {
        final Gold clone = (Gold) gold1.clone();

        assertThat(clone.getAmount()).isEqualTo(gold1.getAmount());
        assertThat(clone).isNotSameAs(gold1);

        final Gold clone2 = (Gold) gold2.clone();

        assertThat(clone2.getAmount()).isEqualTo(gold2.getAmount());
        assertThat(clone2).isNotSameAs(gold2);
    }

}
