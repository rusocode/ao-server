package com.ao.model.worldobject;

public interface EquipableItem extends Item {

    /**
     * Checks if the item is equipped or not.
     *
     * @return true if the item is equipped, false otherwise
     */
    boolean isEquipped();

    /**
     * Sets the item as equipped or not.
     *
     * @param equipped true if the item is equipped, false otherwise
     */
    void setEquipped(boolean equipped);

    /**
     * Retrieves the object's graphic when equipped (maybe a body index, head index, etc. according to the specific item).
     *
     * @return the object's graphic when equipped (maybe a body index, head index, etc. according to the specific item)
     */
    int getEquippedGraphic();

}
