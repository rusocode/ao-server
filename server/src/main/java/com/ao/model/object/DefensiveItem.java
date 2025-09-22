package com.ao.model.object;

/**
 * Represents a defensive item such as an armor or shield.
 */

public interface DefensiveItem extends EquipableItem {

    int getMinDef();

    int getMaxDef();

    int getMinMagicDef();

    int getMaxMagicDef();

}
