package com.ao.model.character.archetype;

import com.ao.model.spell.Spell;
import com.ao.model.object.Boat;
import com.ao.model.object.EquipableItem;
import com.ao.model.object.Weapon;
import com.ao.model.object.properties.crafting.Craftable;

public interface Archetype {

    /**
     * Checks if the user has the skills needed to create the given item.
     *
     * @param smithingSkill user's blacksmithing skill
     * @param item          item to be created
     * @return true if the user can create the item, false otherwise
     */
    boolean canSmithing(int smithingSkill, Craftable item);

    /**
     * Checks if the user has the skills needed to create the given item.
     *
     * @param woodWorkSkill user's woodworking skill
     * @param item          item to be created
     * @return true if the user can create the item, false otherwise
     */
    boolean canCarpentry(int woodWorkSkill, Craftable item);

    /**
     * Checks if the user has the skills needed to sail the given boat.
     *
     * @param sailingSkill user's sailing skill
     * @param boat         boat to be used for sailing
     * @return true if the user can sail the given boat, false otherwise
     */
    boolean canSail(int sailingSkill, Boat boat);

    /**
     * Checks if the archetype allows performing a critical blow.
     *
     * @return true if the archetype allows performing a critical blow, false otherwise
     */
    boolean canCriticalBlow();

    /**
     * Checks if the archetype allows to pick-pocket.
     *
     * @return true if the archetype allows a picking-pocket, false otherwise
     */
    boolean canPickPocket();

    /**
     * Checks if the archetype allows immobilizing .
     *
     * @return true if the archetype allows immobilizing, false otherwise
     */
    boolean canImmobilize();

    /**
     * Checks if the archetype allows disarming an enemy.
     *
     * @return true if the archetype allows disarming an enemy, false otherwise
     */
    boolean canDisarm();

    /**
     * Checks if the user has the skills needed to create the given item.
     *
     * @param stabbingSkill user's stabbing skill
     * @return true if the user can stab an enemy, false otherwise
     */
    boolean canStab(int stabbingSkill);

    /**
     * Checks if the user can cast a given spell.
     *
     * @param spell  spell to be cast
     * @param weapon weapon being equipped by the user
     * @param ring   ring being equipped by the user
     * @return true if the user can cast the given spell, false otherwise
     */
    boolean canCast(Spell spell, Weapon weapon, EquipableItem ring);

    /**
     * Checks if the user can walk while hidden.
     *
     * @return true if the user can walk while hidden, false otherwise
     */
    boolean canWalkHidden();

    /**
     * Retrieves the minimum level required to sail.
     *
     * @return the minimum level required to sail
     */
    int getSailingMinLevel();

    /**
     * Retrieves the stamina required for blacksmithing.
     *
     * @return the stamina required for blacksmithing
     */
    int getBlacksmithingStaminaCost();

    /**
     * Retrieves the stamina required for wood working.
     *
     * @return the stamina required for wood working
     */
    int getWoodWorkStaminaCost();

    /**
     * Retrieves the stamina required for fish.
     *
     * @return the stamina required for fish
     */
    int getFishingStaminaCost();

    /**
     * Retrieves the stamina required for lumberjack.
     *
     * @return the stamina required for lumberjack
     */
    int getLumberjackingStaminaCost();

    /**
     * Retrieves the stamina required for mining.
     *
     * @return the stamina required for mining
     */
    int getMiningStaminaCost();

    /**
     * Retrieves the minimum amount of fish that can be captured.
     *
     * @return the minimum amount of fish that can be captured
     */
    int getFishedMinAmount();

    /**
     * Retrieves the maximum amount of fish that can be captured.
     *
     * @return the maximum amount of fish that can be captured
     */
    int getFishedMaxAmount();

    /**
     * Retrieves the minimum amount of gold that can be stolen.
     *
     * @return the minimum amount of gold that can be stolen
     */
    int getStolenMinAmount();

    /**
     * Retrieves the maximum amount of gold that can be stolen.
     *
     * @return the maximum amount of gold that can be stolen
     */
    int getStolenMaxAmount();

    /**
     * Retrieves the minimum amount of wood that can be lumbered.
     *
     * @return the minimum amount of wood that can be lumbered
     */
    int getLumberedMinAmount();

    /**
     * Retrieves the maximum amount of wood that can be lumbered.
     *
     * @return the maximum amount of wood that can be lumbered
     */
    int getLumberedMaxAmount();

    /**
     * Retrieves the minimum amount of minerals that can be mined.
     *
     * @return the minimum amount of minerals that can be mined
     */
    int getMiningMinAmount();

    /**
     * Retrieves the maximum amount of minerals that can be mined.
     *
     * @return the maximum amount of minerals that can be mined.
     */
    int getMiningMaxAmount();

    /**
     * Retrieve the amount by which to increment the user's hit each level.
     *
     * @param level level the user has just reached
     * @return the amount by which to increment the user's hit
     */
    int getHitIncrement(int level);

    /**
     * Retrieve the amount by which to increment the user's stamina each level.
     *
     * @return the amount by which to increment the user's stamina
     */
    int getStaminaIncrement();

    /**
     * Retrieve the amount by which to increment the user's mana each level
     *
     * @param intelligence user's intelligence
     * @param mana         user's current maximum mana
     * @return the amount by which to increment the user's mana
     */
    int getManaIncrement(int intelligence, int mana);

    /**
     * Retrieves the chance of succeeding at stabbing an opponent. Must be a number between 0 (impossible) and 1 (always)
     *
     * @param stabSkill user's stab skill
     * @return the chance of succeeding at stabbing an opponent
     */
    float getStabbingChance(int stabSkill);

    /**
     * Retrieves the damage multiplier for stabbing an opponent.
     *
     * @return the damage multiplier for stabbing an opponent
     */
    float getStabbingDamageModifier();

    /**
     * Retrieves the chance to train taming. Must be a number between 0 (impossible) and 1 (always)
     *
     * @return the chance to train taming
     */
    float getTamingTrainingChance();

    /**
     * Retrieves the Archetype's evasion modifier.
     *
     * @return the Archetype's evasion modifier
     */
    float getEvasionModifier();

    /**
     * Retrieves the Archetype's melee accuracy modifier.
     *
     * @return the Archetype's melee accuracy modifier
     */
    float getMeleeAccuracyModifier();

    /**
     * Retrieves the Archetype's ranged accuracy modifier.
     *
     * @return the Archetype's ranged accuracy modifier
     */
    float getRangedAccuracyModifier();

    /**
     * Retrieves the Archetype's melee damage modifier.
     *
     * @return the Archetype's melee damage modifier
     */
    float getMeleeDamageModifier();

    /**
     * Retrieves the Archetype's ranged damage modifier.
     *
     * @return the Archetype's ranged damage modifier
     */
    float getRangedDamageModifier();

    /**
     * Retrieves the Archetype's wrestling damage modifier.
     *
     * @return the Archetype's wrestling damage modifier
     */
    float getWrestlingDamageModifier();

    /**
     * Retrieves the Archetype's block power modifier.
     *
     * @return the Archetype's block power modifier
     */
    float getBlockPowerModifier();

    /**
     * Retrieves the Archetype's initial mana.
     *
     * @param intelligence character's intelligence
     * @return the Archetype's initial mana
     */
    int getInitialMana(int intelligence);

}
