package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.EquippableItemProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractEquipableItemTest extends AbstractItemTest {

    protected boolean itemEquipped;

    @Test
    public void testGetEquippedGraphic() {
        assertEquals(((EquippableItemProperties) objectProps).getEquippedGraphic(), ((AbstractEquipableItem) object).getEquippedGraphic());
    }

    @Test
    public void testIsEquipped() {
        assertEquals(itemEquipped, ((AbstractEquipableItem) object).isEquipped());
    }

    @Test
    public void testSetEquipped() {
        final AbstractEquipableItem it = (AbstractEquipableItem) object;
        it.setEquipped(false);
        assertEquals(false, it.isEquipped());
        it.setEquipped(true);
        assertEquals(true, it.isEquipped());
    }

}
