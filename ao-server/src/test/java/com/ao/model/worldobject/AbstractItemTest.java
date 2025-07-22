package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractItemTest extends AbstractWorldObjectTest {

    protected int ammount;

    @Test
    public void testAddAmount() {
        final AbstractItem item = (AbstractItem) object;
        final int ammount = item.getAmount();

        // Check adding and removing
        if (ammount < AbstractItem.MAX_STACKED_ITEMS) {
            item.addAmount(1);
            assertEquals(ammount + 1, item.getAmount());
            item.addAmount(-1);
            assertEquals(ammount, item.getAmount());
        } else {
            item.addAmount(-1);
            assertEquals(ammount - 1, item.getAmount());
            item.addAmount(1);
            assertEquals(ammount, item.getAmount());
        }

        // Check limits
        item.addAmount(AbstractItem.MAX_STACKED_ITEMS + 1);
        assertEquals(AbstractItem.MAX_STACKED_ITEMS, item.getAmount());
    }

    @Test
    public void testGetValue() {
        final AbstractItem item = (AbstractItem) object;
        final ItemProperties itemProps = (ItemProperties) objectProps;
        assertEquals(itemProps.getValue(), item.getValue());
    }

    @Test
    public void testIsNewbie() {
        final AbstractItem item = (AbstractItem) object;
        final ItemProperties itemProps = (ItemProperties) objectProps;
        assertEquals(itemProps.isNewbie(), item.isNewbie());
    }

    @Test
    public void testCanBeStolen() {
        final AbstractItem item = (AbstractItem) object;
        assertTrue(item.canBeStolen());
    }

    @Test
    public void testIsNoLog() {
        final AbstractItem item = (AbstractItem) object;
        final ItemProperties itemProps = (ItemProperties) objectProps;
        assertEquals(itemProps.isNoLog(), item.isNoLog());
    }

    public void testIsFalls() {
        final AbstractItem item = (AbstractItem) object;
        final ItemProperties itemProps = (ItemProperties) objectProps;
        assertEquals(itemProps.isFalls(), item.isFalls());
    }

    @Test
    public void testIsRespawneable() {
        final AbstractItem item = (AbstractItem) object;
        final ItemProperties itemProps = (ItemProperties) objectProps;
        assertEquals(itemProps.isRespawnable(), item.isRespawnable());
    }

}
