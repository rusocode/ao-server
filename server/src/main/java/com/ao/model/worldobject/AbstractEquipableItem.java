package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.EquippableItemProperties;

/**
 * Base class for equipable items.
 */

public abstract class AbstractEquipableItem extends AbstractItem implements EquipableItem {

    protected boolean equipped;

    /**
     * Creates a new AbstractEquipableItem instance.
     *
     * @param properties item's properties
     * @param amount     item's amount
     */
    public AbstractEquipableItem(EquippableItemProperties properties, int amount) {
        super(properties, amount);
        this.equipped = false;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.EquipableItem#getEquippedGraphic()
     */
    @Override
    public int getEquippedGraphic() {
        return ((EquippableItemProperties) properties).getEquippedGraphic();
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.EquipableItem#isEquipped()
     */
    @Override
    public boolean isEquipped() {
        return equipped;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.model.worldobject.EquipableItem#setEquipped(boolean)
     */
    @Override
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

}
