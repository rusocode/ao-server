package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDefensiveItemTest extends AbstractEquipableItemTest {

    @Test
    public void testGetMaxDef() {
        assertEquals(((DefensiveItemProperties) objectProps).getMaxDef(), ((AbstractDefensiveItem) object).getMaxDef());
    }

    @Test
    public void testGetMinDef() {
        assertEquals(((DefensiveItemProperties) objectProps).getMinDef(), ((AbstractDefensiveItem) object).getMinDef());
    }

    @Test
    public void testGetMaxMagicDef() {
        assertEquals(((DefensiveItemProperties) objectProps).getMaxMagicDef(), ((AbstractDefensiveItem) object).getMaxMagicDef());
    }

    @Test
    public void testGetMinMagicDef() {
        assertEquals(((DefensiveItemProperties) objectProps).getMinMagicDef(), ((AbstractDefensiveItem) object).getMinMagicDef());
    }

}
