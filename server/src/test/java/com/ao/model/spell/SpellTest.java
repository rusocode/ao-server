package com.ao.model.spell;

import com.ao.exception.InvalidTargetException;
import com.ao.mock.MockFactory;
import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.worldobject.WorldObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SpellTest {

    private static final int REQUIRED_MANA = 10;
    private static final int REQUIRED_STAFF_POWER = 10;
    private static final int REQUIRED_SKILL = 10;
    private static final String SPELL_NAME = "Poison";
    private static final String SPELL_DESCRIPTION = "This spell poisons the target character";
    private static final boolean IS_NEGATIVE = true;
    private static final int SPELL_FX = 1;
    private static final int SPELL_SOUND = 2;
    private static final String SPELL_MAGIC_WORDS = "magic words for poison spell";

    private Spell spellNoStaff;
    private Spell spellWithStaff;
    private Spell spellNoStaffObject;

    @Before
    public void setUp() throws Exception {
        final Effect[] effects = new Effect[2];
        effects[0] = MockFactory.mockEffect(true, false);
        effects[1] = MockFactory.mockEffect(true, false);

        spellNoStaff = new Spell(1, effects, 0, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);

        final Effect[] effects2 = new Effect[2];
        effects2[0] = MockFactory.mockEffect(true, false);
        effects2[1] = MockFactory.mockEffect(true, false);

        spellWithStaff = new Spell(1, effects2, REQUIRED_STAFF_POWER, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);

        final Effect[] effects3 = new Effect[2];
        effects3[0] = MockFactory.mockEffect(false, true);
        effects3[1] = MockFactory.mockEffect(false, true);

        spellNoStaffObject = new Spell(1, effects3, 0, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);
    }

    @Test
    public void testRequiresStaff() {
        assertFalse(spellNoStaff.requiresStaff());
        assertTrue(spellWithStaff.requiresStaff());
        assertFalse(spellNoStaffObject.requiresStaff());
    }

    @Test
    public void testGetRequiredStaffPower() {
        assertEquals(0, spellNoStaff.getRequiredStaffPower());
        assertEquals(REQUIRED_STAFF_POWER, spellWithStaff.getRequiredStaffPower());
        assertEquals(0, spellNoStaffObject.getRequiredStaffPower());
    }

    @Test
    public void testGetRequiredMana() {
        assertEquals(REQUIRED_MANA, spellNoStaff.getRequiredMana());
        assertEquals(REQUIRED_MANA, spellWithStaff.getRequiredMana());
        assertEquals(REQUIRED_MANA, spellNoStaffObject.getRequiredMana());
    }

    @Test
    public void testGetRequiredSkill() {
        assertEquals(REQUIRED_SKILL, spellNoStaff.getRequiredSkill());
        assertEquals(REQUIRED_SKILL, spellWithStaff.getRequiredSkill());
        assertEquals(REQUIRED_SKILL, spellNoStaffObject.getRequiredSkill());
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        final Character caster = MockFactory.mockCharacter();
        final Character target = MockFactory.mockCharacter();

        assertTrue(spellNoStaff.appliesTo(caster, target));
        assertTrue(spellWithStaff.appliesTo(caster, target));
        assertFalse(spellNoStaffObject.appliesTo(caster, target));
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        final Character caster = MockFactory.mockCharacter();
        final WorldObject target = MockFactory.mockWorldObject();

        assertFalse(spellNoStaff.appliesTo(caster, target));
        assertFalse(spellWithStaff.appliesTo(caster, target));
        assertTrue(spellNoStaffObject.appliesTo(caster, target));
    }

    @Test
    public void testApplyCharacterCharacter() {
        final Character caster = MockFactory.mockCharacter();
        final Character target = MockFactory.mockCharacter();

        spellNoStaff.apply(caster, target);
        spellWithStaff.apply(caster, target);

        try {
            spellNoStaffObject.apply(caster, target);
            fail("Effect not targeting character was applied succesfully to one.");
        } catch (final InvalidTargetException e) {
            // This is ok
        }

        // Check mana cost
        verify(caster, times(2)).addToMana(-REQUIRED_MANA);
    }

    @Test
    public void testApplyCharacterWorldObject() {
        final Character caster = MockFactory.mockCharacter();
        final WorldObject target = MockFactory.mockWorldObject();

        try {
            spellNoStaff.apply(caster, target);
            fail("Effect not targeting world objects was applied succesfully to one.");
        } catch (final InvalidTargetException e) {
            // This is ok
        }

        try {
            spellWithStaff.apply(caster, target);
            fail("Effect not targeting world objects was applied succesfully to one.");
        } catch (InvalidTargetException e) {
            // This is ok
        }

        // Nothing else should happen to caster nor target
        Mockito.verifyNoInteractions(caster, target);

        spellNoStaffObject.apply(caster, target);

        // Check mana cost
        verify(caster).addToMana(-REQUIRED_MANA);
    }

    @Test
    public void testGetName() {
        assertEquals(SPELL_NAME, spellNoStaff.getName());
        assertEquals(SPELL_NAME, spellWithStaff.getName());
        assertEquals(SPELL_NAME, spellNoStaffObject.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals(SPELL_DESCRIPTION, spellNoStaff.getDescription());
        assertEquals(SPELL_DESCRIPTION, spellWithStaff.getDescription());
        assertEquals(SPELL_DESCRIPTION, spellNoStaffObject.getDescription());
    }

    @Test
    public void testIsNegative() {
        assertEquals(IS_NEGATIVE, spellNoStaff.isNegative());
        assertEquals(IS_NEGATIVE, spellWithStaff.isNegative());
        assertEquals(IS_NEGATIVE, spellNoStaffObject.isNegative());
    }

    @Test
    public void testGetFX() {
        assertEquals(SPELL_FX, spellNoStaff.getFX());
        assertEquals(SPELL_FX, spellWithStaff.getFX());
        assertEquals(SPELL_FX, spellNoStaffObject.getFX());
    }

    @Test
    public void testGetSound() {
        assertEquals(SPELL_SOUND, spellNoStaff.getSound());
        assertEquals(SPELL_SOUND, spellWithStaff.getSound());
        assertEquals(SPELL_SOUND, spellNoStaffObject.getSound());
    }

    @Test
    public void testGetMagicWords() {
        assertEquals(SPELL_MAGIC_WORDS, spellNoStaff.getMagicWords());
        assertEquals(SPELL_MAGIC_WORDS, spellWithStaff.getMagicWords());
        assertEquals(SPELL_MAGIC_WORDS, spellNoStaffObject.getMagicWords());
    }

}
