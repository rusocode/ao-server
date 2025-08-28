package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.EquippableItemProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractEquipableItemTest extends AbstractItemTest {

    protected boolean itemEquipped;

    @Test
    public void testGetEquippedGraphic() {
        assertThat(((AbstractEquipableItem) object).getEquippedGraphic()).isEqualTo(((EquippableItemProperties) objectProps).getEquippedGraphic());
    }

    @Test
    public void testIsEquipped() {
        assertThat(((AbstractEquipableItem) object).isEquipped()).isEqualTo(itemEquipped);
    }

    @Test
    public void testSetEquipped() {
        AbstractEquipableItem it = (AbstractEquipableItem) object;
        it.setEquipped(false);
        assertThat(it.isEquipped()).isFalse();
        it.setEquipped(true);
        assertThat(it.isEquipped()).isTrue();
    }

}
