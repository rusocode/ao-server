package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.object.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PoisonEffectTest {

    private Effect poisonEffect;

    @BeforeEach
    public void setUp() throws Exception {
        poisonEffect = new PoisonEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character caster = mock(Character.class);
        Character target = mock(Character.class);

        poisonEffect.apply(caster, target);
        verify(target).setPoisoned(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character caster = mock(Character.class);
        Character deadTarget = mock(Character.class);
        Character aliveTarget = mock(Character.class);
        when(deadTarget.isDead()).thenReturn(Boolean.TRUE);

        // Paralyzing a dead char is invalid
        assertThat(poisonEffect.appliesTo(caster, deadTarget)).isFalse();

        // Paralyzing a live char is valid
        assertThat(poisonEffect.appliesTo(caster, aliveTarget)).isTrue();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);

        // Should always false, no matter what
        assertThat(poisonEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);

        // Should do nothing....
        try {
            poisonEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // This is ok
        }
    }

}
