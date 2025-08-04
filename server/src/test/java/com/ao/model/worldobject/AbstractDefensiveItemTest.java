package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDefensiveItemTest extends AbstractEquipableItemTest {

    @Test
    public void testGetMaxDef() {
        assertThat(((AbstractDefensiveItem) object).getMaxDef()).isEqualTo(((DefensiveItemProperties) objectProps).getMaxDef());
    }

    @Test
    public void testGetMinDef() {
        assertThat(((AbstractDefensiveItem) object).getMinDef()).isEqualTo(((DefensiveItemProperties) objectProps).getMinDef());
    }

    @Test
    public void testGetMaxMagicDef() {
        assertThat(((AbstractDefensiveItem) object).getMaxMagicDef()).isEqualTo(((DefensiveItemProperties) objectProps).getMaxMagicDef());
    }

    @Test
    public void testGetMinMagicDef() {
        assertThat(((AbstractDefensiveItem) object).getMinMagicDef()).isEqualTo(((DefensiveItemProperties) objectProps).getMinMagicDef());
    }

}
