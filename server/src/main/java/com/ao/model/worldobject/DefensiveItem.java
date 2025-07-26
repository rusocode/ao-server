package com.ao.model.worldobject;

/**
 * Represents a defensive item such as an armor or shield.
 */

public interface DefensiveItem extends EquipableItem {

    /**
     * Retrieves the minimum defense granted.
     *
     * @return the minimum defense granted.
     */
    int getMinDef();

    /**
     * Retrieves the maximum defense granted.
     *
     * @return the maximum defense granted
     */
    int getMaxDef();

    /**
     * Retrieves the minimum magic defense granted.
     *
     * @return the minimum magic defense granted
     */
    int getMinMagicDef();

    /**
     * Retrieves the maximum magic defense granted.
     *
     * @return the maximum magic defense granted
     */
    int getMaxMagicDef();

}
