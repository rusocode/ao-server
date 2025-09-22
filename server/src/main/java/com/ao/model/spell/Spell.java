package com.ao.model.spell;

import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.worldobject.Object;

public class Spell {

    private final int requiredStaffPower;

    private final int requiredSkill;

    private final int requiredMana;

    private final String name;

    private final String description;

    /**
     * Determines whether the effects applied to target are harmful, or not.
     */
    private final boolean negative;

    private final int fx;

    private final int sound;

    private final String magicWords;

    protected int id;

    protected Effect[] effects;

    public Spell(int id, Effect[] effects, int requiredStaffPower, int requiredSkill, int requiredMana, String name,
                 String description, boolean negative, int fx, int sound, String magicWords) {
        this.id = id;
        this.effects = effects;
        this.requiredStaffPower = requiredStaffPower;
        this.requiredSkill = requiredSkill;
        this.requiredMana = requiredMana;
        this.name = name;
        this.description = description;
        this.negative = negative;
        this.fx = fx;
        this.sound = sound;
        this.magicWords = magicWords;
    }

    public boolean requiresStaff() {
        return requiredStaffPower > 0;
    }

    public int getRequiredStaffPower() {
        return requiredStaffPower;
    }

    public int getRequiredMana() {
        return requiredMana;
    }

    public int getRequiredSkill() {
        return requiredSkill;
    }

    /**
     * Checks whether the spell can be applied to the given target when is cast by the given caster.
     *
     * @param caster spell's caster.
     * @param target spell's target.
     * @return true if the spell applies to the target and caster, false otherwise
     */
    public boolean appliesTo(Character caster, Character target) {
        for (Effect effect : effects)
            if (!effect.appliesTo(caster, target))
                return false;
        return true;
    }

    /**
     * Checks whether the spell can be applied to the given target when is casted by the given caster.
     *
     * @param caster spell's caster
     * @param target spell's target
     * @return true if the spell applies to the target and caster, false otherwise
     */
    public boolean appliesTo(Character caster, Object target) {
        for (int i = 0; i < effects.length; i++)
            if (!effects[i].appliesTo(caster, target))
                return false;
        return true;
    }

    /**
     * Applies the spell to the given target.
     *
     * @param caster spell's caster
     * @param target target to apply the spell
     */
    public void apply(Character caster, Character target) {
        for (int i = 0; i < effects.length; i++)
            effects[i].apply(caster, target);
        caster.addToMana(-requiredMana);
    }

    /**
     * Applies the spell to the given target.
     *
     * @param caster spell's caster
     * @param target target to apply the spell
     */
    public void apply(Character caster, Object target) {
        for (int i = 0; i < effects.length; i++)
            effects[i].apply(caster, target);
        caster.addToMana(-requiredMana);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Checks whether the spell is negative to target or not.
     */
    public boolean isNegative() {
        return negative;
    }

    public int getFX() {
        return fx;
    }

    public int getSound() {
        return sound;
    }

    public String getMagicWords() {
        return magicWords;
    }

    public int getId() {
        return id;
    }

}
