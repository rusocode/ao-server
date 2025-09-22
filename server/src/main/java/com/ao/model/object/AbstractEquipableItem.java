package com.ao.model.object;

import com.ao.model.object.properties.EquippableItemProperties;

/**
 * Base class for equipable items.
 */

public abstract class AbstractEquipableItem extends AbstractItem implements EquipableItem {

    protected boolean equipped;

    public AbstractEquipableItem(EquippableItemProperties properties, int amount) {
        super(properties, amount);
        this.equipped = false;
    }

    @Override
    public int getEquippedGraphic() {
        return ((EquippableItemProperties) properties).getEquippedGraphic();
    }

    @Override
    public boolean isEquipped() {
        return equipped;
    }

    @Override
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

}
