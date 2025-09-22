package com.ao.model.worldobject.properties.crafting;

import com.ao.model.worldobject.properties.ObjectProperties;

/**
 * Defines a craftable object. Allows a lightweight pattern implementation.
 */

public class Craftable {

    protected ObjectProperties object;

    protected CraftingSkill craftingSkill;
    protected int craftingSkillPoints;
    protected int requiredWood;
    protected int requiredElvenWood;
    protected int requiredGoldIngot;
    protected int requiredSilverIngot;
    protected int requiredIronIngot;

    public Craftable(ObjectProperties object, CraftingSkill craftingSkill, int craftingSkillPoints, int requiredWood, int requiredElvenWood, int requiredGoldIngot, int requiredSilverIngot, int requiredIronIngot) {
        super(); // TODO Es necesario?
        this.object = object;
        this.craftingSkill = craftingSkill;
        this.craftingSkillPoints = craftingSkillPoints;
        this.requiredWood = requiredWood;
        this.requiredElvenWood = requiredElvenWood;
        this.requiredGoldIngot = requiredGoldIngot;
        this.requiredSilverIngot = requiredSilverIngot;
        this.requiredIronIngot = requiredIronIngot;
    }

    public CraftingSkill getCraftingSkill() {
        return craftingSkill;
    }

    public int getCraftingSkillPoints() {
        return craftingSkillPoints;
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

    public ObjectProperties getObject() {
        return object;
    }

}
