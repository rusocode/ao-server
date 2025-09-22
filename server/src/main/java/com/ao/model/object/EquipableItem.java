package com.ao.model.object;

public interface EquipableItem extends Item {

    boolean isEquipped();

    void setEquipped(boolean equipped);

    /**
     * Retrieves the object's graphic when equipped (maybe a body index, head index, etc. according to the specific item).
     *
     * @return the object's graphic when equipped (maybe a body index, head index, etc. according to the specific item)
     */
    int getEquippedGraphic();

}
