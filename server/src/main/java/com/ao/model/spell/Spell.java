package com.ao.model.spell;

import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.worldobject.WorldObject;

/**
 * Defines a spell's model.
 */

public class Spell {

    /**
     * The required staff power to use the spell.
     */
    private final int requiredStaffPower;

    /**
     * The required skill to use the spell.
     */
    private final int requiredSkill;

    /**
     * The required mana to use the spell.
     */
    private final int requiredMana;

    /**
     * The spell's name.
     */
    private final String name;

    /**
     * The spell's description.
     */
    private final String description;

    /**
     * Determines whether the effects applied to target are harmful, or not.
     */
    private final boolean negative;

    /**
     * The spell's fx.
     */
    private final int fx;

    /**
     * The spell's sound.
     */
    private final int sound;

    /**
     * The pronounced words when the spell is thrown.
     */
    private final String magicWords;

    /**
     * The id of the spell.
     */
    protected int id;

    /**
     * The effects stack.
     */
    protected Effect[] effects;

    /**
     * Instantes the spell.
     *
     * @param id                 spell's id
     * @param effects            spell's effect
     * @param requiredStaffPower spell's required staff power
     * @param requiredSkill      spell's required skill
     * @param requiredMana       spell's required mana
     * @param name               spell's name
     * @param description        spell's description
     * @param negative           whether the spell is negative or not for the target
     * @param fx                 fx to use when displaying the spell
     * @param sound              sound to play when casting the spell
     * @param magicWords         magic words to be said by the caster when casting the spell
     */
    public Spell(int id, Effect[] effects, int requiredStaffPower, int requiredSkill,
                 int requiredMana, String name, String description, boolean negative,
                 int fx, int sound, String magicWords) {
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

    /**
     * Checks whether the spells require a staff to be used or not.
     *
     * @return true if the spells require a staff, false otherwise
     */
    public boolean requiresStaff() {
        return requiredStaffPower > 0;
    }

    /**
     * Retrieves the required staff power.
     *
     * @return the staff power required to use the spell
     */
    public int getRequiredStaffPower() {
        return requiredStaffPower;
    }

    /**
     * Retrieves the spell's required mana points.
     *
     * @return the spell's required mana
     */
    public int getRequiredMana() {
        return requiredMana;
    }

    /**
     * Retrieves the spell's required skills.
     *
     * @return the spell's required skills
     */
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
    public boolean appliesTo(Character caster, WorldObject target) {
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
    public void apply(Character caster, WorldObject target) {
        for (int i = 0; i < effects.length; i++)
            effects[i].apply(caster, target);
        caster.addToMana(-requiredMana);
    }

    /**
     * Retrieves the spell's name.
     *
     * @return Spell's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the spell's description.
     *
     * @return Spell's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks whether the spell is negative to target or not.
     */
    public boolean isNegative() {
        return negative;
    }

    /**
     * Retrieves the spell's fx.
     *
     * @return Spell's fx
     */
    public int getFX() {
        return fx;
    }

    /**
     * Retrieves the spell's sound.
     *
     * @return Spell's sound
     */
    public int getSound() {
        return sound;
    }

    /**
     * Retrieves the spell's magic words.
     *
     * @return spell's magic words
     */
    public String getMagicWords() {
        return magicWords;
    }

    /**
     * Retrieves the spell's id.
     *
     * @return spells id
     */
    public int getId() {
        return id;
    }

}
