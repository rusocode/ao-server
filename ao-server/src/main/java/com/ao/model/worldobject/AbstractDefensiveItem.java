package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DefensiveItemProperties;

/**
 * Base class for defensive items.
 */
public abstract class AbstractDefensiveItem extends AbstractEquipableItem implements
		DefensiveItem {
	
	/**
	 * Creates a new AbstractDefensiveItem instance.
	 * @param properties The item's properties.
	 * @param amount The item's amount.
	 */
	public AbstractDefensiveItem(DefensiveItemProperties properties, int amount) {
		super(properties, amount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.DefensiveItem#getMaxDef()
	 */
	@Override
	public int getMaxDef() {
		return ((DefensiveItemProperties) properties).getMaxDef();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.DefensiveItem#getMinDef()
	 */
	@Override
	public int getMinDef() {
		return ((DefensiveItemProperties) properties).getMinDef();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.DefensiveItem#getMinMagicDef()
	 */
	@Override
	public int getMinMagicDef() {
		return ((DefensiveItemProperties) properties).getMinMagicDef();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.model.worldobject.DefensiveItem#getMaxMagicDef()
	 */
	@Override
	public int getMaxMagicDef() {
		return ((DefensiveItemProperties) properties).getMaxMagicDef();
	}
}
