package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractItemTest extends AbstractObjectTest {

    protected int ammount;

    @Test
    public void testAddAmount() {
        AbstractItem item = (AbstractItem) object;
        int ammount = item.getAmount();

        // Check adding and removing
        if (ammount < AbstractItem.MAX_STACKED_ITEMS) {
            item.addAmount(1);
            assertThat(item.getAmount()).isEqualTo(ammount + 1);
            item.addAmount(-1);
            assertThat(item.getAmount()).isEqualTo(ammount);
        } else {
            item.addAmount(-1);
            assertThat(item.getAmount()).isEqualTo(ammount - 1);
            item.addAmount(1);
            assertThat(item.getAmount()).isEqualTo(ammount);
        }

        // Check limits
        item.addAmount(AbstractItem.MAX_STACKED_ITEMS + 1);
        assertThat(item.getAmount()).isEqualTo(AbstractItem.MAX_STACKED_ITEMS);
    }

    @Test
    public void testGetValue() {
        AbstractItem item = (AbstractItem) object;
        ItemProperties itemProps = (ItemProperties) objectProps;
        assertThat(item.getValue()).isEqualTo(itemProps.getValue());
    }

    @Test
    public void testIsNewbie() {
        AbstractItem item = (AbstractItem) object;
        ItemProperties itemProps = (ItemProperties) objectProps;
        assertThat(item.isNewbie()).isEqualTo(itemProps.isNewbie());
    }

    @Test
    public void testCanBeStolen() {
        AbstractItem item = (AbstractItem) object;
        assertThat(item.canBeStolen()).isTrue();
    }

    @Test
    public void testIsNoLog() {
        AbstractItem item = (AbstractItem) object;
        ItemProperties itemProps = (ItemProperties) objectProps;
        assertThat(item.isNoLog()).isEqualTo(itemProps.isNoLog());
    }

    public void testIsFalls() {
        AbstractItem item = (AbstractItem) object;
        ItemProperties itemProps = (ItemProperties) objectProps;
        assertThat(item.isFalls()).isEqualTo(itemProps.isFalls());
    }

    @Test
    public void testIsRespawneable() {
        AbstractItem item = (AbstractItem) object;
        ItemProperties itemProps = (ItemProperties) objectProps;
        assertThat(item.isRespawnable()).isEqualTo(itemProps.isRespawnable());
    }

}
