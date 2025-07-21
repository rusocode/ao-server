

package com.ao.model.worldobject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ao.model.worldobject.properties.DefensiveItemProperties;

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
