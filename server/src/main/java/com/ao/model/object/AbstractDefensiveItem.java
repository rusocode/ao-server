package com.ao.model.object;

import com.ao.model.object.properties.DefensiveItemProperties;

/**
 * Base class for defensive items.
 */

public abstract class AbstractDefensiveItem extends AbstractEquipableItem implements DefensiveItem {

    public AbstractDefensiveItem(DefensiveItemProperties properties, int amount) {
        super(properties, amount);
    }

    @Override
    public int getMaxDef() {
        return ((DefensiveItemProperties) properties).getMaxDef();
    }

    @Override
    public int getMinDef() {
        return ((DefensiveItemProperties) properties).getMinDef();
    }

    @Override
    public int getMinMagicDef() {
        return ((DefensiveItemProperties) properties).getMinMagicDef();
    }

    @Override
    public int getMaxMagicDef() {
        return ((DefensiveItemProperties) properties).getMaxMagicDef();
    }

}
