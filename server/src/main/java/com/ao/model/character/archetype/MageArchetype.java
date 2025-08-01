package com.ao.model.character.archetype;

import com.ao.model.spell.Spell;
import com.ao.model.worldobject.EquipableItem;
import com.ao.model.worldobject.Staff;
import com.ao.model.worldobject.Weapon;

/**
 * A mage archetype.
 */

public class MageArchetype extends DefaultArchetype {

    private static final float MANA_MODIFIER = 2.8f;
    private static final int HIT_INCREMENT = 1;
    private static final int STAMINA_INCREMENT = 14;
    private static final int INITIAL_MANA_MODIFIER = 3;

    public MageArchetype(float evasionModifier, float meleeAccuracyModifier,
                         float rangedAccuracyModifier, float meleeDamageModifier,
                         float rangedDamageModifier, float wrestlingDamageModifier,
                         float blockPowerModifier) {
        super(evasionModifier, meleeAccuracyModifier, rangedAccuracyModifier,
                meleeDamageModifier, rangedDamageModifier, wrestlingDamageModifier,
                blockPowerModifier);
    }

    @Override
    public boolean canCast(Spell spell, Weapon weapon, EquipableItem ring) {
        return (spell.requiresStaff() && weapon instanceof Staff && spell.getRequiredStaffPower() < ((Staff) weapon).getMagicPower());
    }

    @Override
    public int getStaminaIncrement() {
        return STAMINA_INCREMENT;
    }

    @Override
    public int getHitIncrement(int level) {
        return HIT_INCREMENT;
    }

    @Override
    public int getManaIncrement(int intelligence, int mana) {
        return Math.round(intelligence * MANA_MODIFIER);
    }

    @Override
    public int getInitialMana(int intelligence) {
        return intelligence * INITIAL_MANA_MODIFIER;
    }

}
