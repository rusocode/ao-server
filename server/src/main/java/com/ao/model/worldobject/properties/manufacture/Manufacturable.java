package com.ao.model.worldobject.properties.manufacture;

import com.ao.model.worldobject.properties.WorldObjectProperties;

/**
 * Defines a manufacturable item.
 */

public class Manufacturable {

    protected WorldObjectProperties object;

    protected ManufactureType manufactureType;

    protected int manufactureDifficulty;
    protected int requiredWood;
    protected int requiredElvenWood;
    protected int requiredGoldIngot;
    protected int requiredSilverIngot;
    protected int requiredIronIngot;

    /**
     * Create a new manufacturable instance.
     *
     * @param object                object that can be manufactured
     * @param manufactureType       type of manufacturing skill required
     * @param manufactureDifficulty difficulty to manufacture this item
     * @param requiredWood          wood required for this manufacture
     * @param requiredElvenWood     elven wood required for this manufacture
     * @param requiredGoldIngot     gold ingots required for this manufacture
     * @param requiredSilverIngot   silver ingots required for this manufacture
     * @param requiredIronIngot     iron ingots required for this manufacture
     */
    public Manufacturable(WorldObjectProperties object, ManufactureType manufactureType, int manufactureDifficulty, int requiredWood, int requiredElvenWood,
                          int requiredGoldIngot, int requiredSilverIngot, int requiredIronIngot) {
        super();
        this.object = object;
        this.manufactureType = manufactureType;
        this.manufactureDifficulty = manufactureDifficulty;
        this.requiredWood = requiredWood;
        this.requiredElvenWood = requiredElvenWood;
        this.requiredGoldIngot = requiredGoldIngot;
        this.requiredSilverIngot = requiredSilverIngot;
        this.requiredIronIngot = requiredIronIngot;
    }

    /**
     * Retrieves the type of manufacturable the item is.
     *
     * @return the type of manufacturable the described item is
     */
    public ManufactureType getManufactureType() {
        return manufactureType;
    }

    /**
     * Retrieves the difficulty to manufacture this item.
     *
     * @return the difficulty to manufacture this item
     */
    public int getManufactureDifficulty() {
        return manufactureDifficulty;
    }

    public int getRequiredWood() {
        return requiredWood;
    }

    public int getRequiredElvenWood() {
        return requiredElvenWood;
    }

    public int getRequiredGoldIngot() {
        return requiredGoldIngot;
    }

    public int getRequiredSilverIngot() {
        return requiredSilverIngot;
    }

    public int getRequiredIronIngot() {
        return requiredIronIngot;
    }

    public WorldObjectProperties getObject() {
        return object;
    }

}
