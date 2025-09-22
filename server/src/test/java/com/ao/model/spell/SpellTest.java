package com.ao.model.spell;

import com.ao.exception.InvalidTargetException;
import com.ao.mock.MockFactory;
import com.ao.model.character.Character;
import com.ao.model.spell.effect.Effect;
import com.ao.model.object.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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

    @BeforeEach
    public void setUp() throws Exception {
        Effect[] effects = new Effect[2];
        effects[0] = MockFactory.mockEffect(true, false);
        effects[1] = MockFactory.mockEffect(true, false);

        spellNoStaff = new Spell(1, effects, 0, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);

        Effect[] effects2 = new Effect[2];
        effects2[0] = MockFactory.mockEffect(true, false);
        effects2[1] = MockFactory.mockEffect(true, false);

        spellWithStaff = new Spell(1, effects2, REQUIRED_STAFF_POWER, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);

        Effect[] effects3 = new Effect[2];
        effects3[0] = MockFactory.mockEffect(false, true);
        effects3[1] = MockFactory.mockEffect(false, true);

        spellNoStaffObject = new Spell(1, effects3, 0, REQUIRED_SKILL, REQUIRED_MANA, SPELL_NAME, SPELL_DESCRIPTION, IS_NEGATIVE, SPELL_FX, SPELL_SOUND, SPELL_MAGIC_WORDS);
    }

    @Test
    public void testRequiresStaff() {
        assertThat(spellNoStaff.requiresStaff()).isFalse();
        assertThat(spellWithStaff.requiresStaff()).isTrue();
        assertThat(spellNoStaffObject.requiresStaff()).isFalse();
    }

    @Test
    public void testGetRequiredStaffPower() {
        assertThat(spellNoStaff.getRequiredStaffPower()).isEqualTo(0);
        assertThat(spellWithStaff.getRequiredStaffPower()).isEqualTo(REQUIRED_STAFF_POWER);
        assertThat(spellNoStaffObject.getRequiredStaffPower()).isEqualTo(0);
    }

    @Test
    public void testGetRequiredMana() {
        assertThat(spellNoStaff.getRequiredMana()).isEqualTo(REQUIRED_MANA);
        assertThat(spellWithStaff.getRequiredMana()).isEqualTo(REQUIRED_MANA);
        assertThat(spellNoStaffObject.getRequiredMana()).isEqualTo(REQUIRED_MANA);
    }

    @Test
    public void testGetRequiredSkill() {
        assertThat(spellNoStaff.getRequiredSkill()).isEqualTo(REQUIRED_SKILL);
        assertThat(spellWithStaff.getRequiredSkill()).isEqualTo(REQUIRED_SKILL);
        assertThat(spellNoStaffObject.getRequiredSkill()).isEqualTo(REQUIRED_SKILL);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character caster = MockFactory.mockCharacter();
        Character target = MockFactory.mockCharacter();

        assertThat(spellNoStaff.appliesTo(caster, target)).isTrue();
        assertThat(spellWithStaff.appliesTo(caster, target)).isTrue();
        assertThat(spellNoStaffObject.appliesTo(caster, target)).isFalse();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Character caster = MockFactory.mockCharacter();
        Object target = MockFactory.mockWorldObject();

        assertThat(spellNoStaff.appliesTo(caster, target)).isFalse();
        assertThat(spellWithStaff.appliesTo(caster, target)).isFalse();
        assertThat(spellNoStaffObject.appliesTo(caster, target)).isTrue();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character caster = MockFactory.mockCharacter();
        Character target = MockFactory.mockCharacter();

        spellNoStaff.apply(caster, target);
        spellWithStaff.apply(caster, target);

        try {
            spellNoStaffObject.apply(caster, target);
            fail("Effect not targeting character was applied succesfully to one.");
        } catch (InvalidTargetException e) {
            // This is ok
        }

        // Check mana cost
        verify(caster, times(2)).addToMana(-REQUIRED_MANA);
    }

    @Test
    public void testApplyCharacterWorldObject() {
        Character caster = MockFactory.mockCharacter();
        Object target = MockFactory.mockWorldObject();

        try {
            spellNoStaff.apply(caster, target);
            fail("Effect not targeting world objects was applied succesfully to one.");
        } catch (InvalidTargetException e) {
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
        assertThat(spellNoStaff.getName()).isEqualTo(SPELL_NAME);
        assertThat(spellWithStaff.getName()).isEqualTo(SPELL_NAME);
        assertThat(spellNoStaffObject.getName()).isEqualTo(SPELL_NAME);
    }

    @Test
    public void testGetDescription() {
        assertThat(spellNoStaff.getDescription()).isEqualTo(SPELL_DESCRIPTION);
        assertThat(spellWithStaff.getDescription()).isEqualTo(SPELL_DESCRIPTION);
        assertThat(spellNoStaffObject.getDescription()).isEqualTo(SPELL_DESCRIPTION);
    }

    @Test
    public void testIsNegative() {
        assertThat(spellNoStaff.isNegative()).isEqualTo(IS_NEGATIVE);
        assertThat(spellWithStaff.isNegative()).isEqualTo(IS_NEGATIVE);
        assertThat(spellNoStaffObject.isNegative()).isEqualTo(IS_NEGATIVE);
    }

    @Test
    public void testGetFX() {
        assertThat(spellNoStaff.getFX()).isEqualTo(SPELL_FX);
        assertThat(spellWithStaff.getFX()).isEqualTo(SPELL_FX);
        assertThat(spellNoStaffObject.getFX()).isEqualTo(SPELL_FX);
    }

    @Test
    public void testGetSound() {
        assertThat(spellNoStaff.getSound()).isEqualTo(SPELL_SOUND);
        assertThat(spellWithStaff.getSound()).isEqualTo(SPELL_SOUND);
        assertThat(spellNoStaffObject.getSound()).isEqualTo(SPELL_SOUND);
    }

    @Test
    public void testGetMagicWords() {
        assertThat(spellNoStaff.getMagicWords()).isEqualTo(SPELL_MAGIC_WORDS);
        assertThat(spellWithStaff.getMagicWords()).isEqualTo(SPELL_MAGIC_WORDS);
        assertThat(spellNoStaffObject.getMagicWords()).isEqualTo(SPELL_MAGIC_WORDS);
    }

}
