package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ParalysisEffectTest {

    private Effect paralysisEffect;

    @BeforeEach
    public void setUp() throws Exception {
        paralysisEffect = new ParalysisEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character caster = mock(Character.class);
        Character target = mock(Character.class);

        paralysisEffect.apply(caster, target);

        verify(target).setParalyzed(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character caster = mock(Character.class);
        Character deadTarget = mock(Character.class);
        when(deadTarget.isDead()).thenReturn(Boolean.TRUE);
        Character aliveTarget = mock(Character.class);
        // Paralyzing a dead char is invalid
        assertThat(paralysisEffect.appliesTo(caster, deadTarget)).isFalse();
        // Paralyzing a live char is valid
        assertThat(paralysisEffect.appliesTo(caster, aliveTarget)).isTrue();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        WorldObject obj = mock(WorldObject.class);
        Character caster = mock(Character.class);
        // Should always false, no matter what
        assertThat(paralysisEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        WorldObject obj = mock(WorldObject.class);
        Character caster = mock(Character.class);
        // Should do nothing....
        try {
            paralysisEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // This is ok
        }
    }

}
