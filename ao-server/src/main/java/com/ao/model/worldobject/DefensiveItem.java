package com.ao.model.worldobject;

/**
 * Represents a defensive item such as an armor or shield.
 */
public interface DefensiveItem extends EquipableItem {

	/**
	 * Retrieves the minimum defense granted.
	 * @return The minimum defense granted.
	 */
	int getMinDef();
	
	/**
	 * Retrieves the maximum defense granted.
	 * @return The maximum defense granted.
	 */
	int getMaxDef();
	
	/**
	 * Retrieves the minimum magic defense granted.
	 * @return The minimum magic defense granted.
	 */
	int getMinMagicDef();

	/**
	 * Retrieves the maximum magic defense granted.
	 * @return The maximum magic defense granted.
	 */
	int getMaxMagicDef();
}
