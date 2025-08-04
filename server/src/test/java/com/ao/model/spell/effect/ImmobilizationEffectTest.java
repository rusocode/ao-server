package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ImmobilizationEffectTest {

    private Effect immobilizationEffect;

    @BeforeEach
    public void setUp() throws Exception {
        immobilizationEffect = new ImmobilizationEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        final Character caster = mock(Character.class);
        final Character target = mock(Character.class);
        immobilizationEffect.apply(caster, target);
        verify(target).setImmobilized(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        final Character caster = mock(Character.class);
        final Character deadTarget = mock(Character.class);
        when(deadTarget.isDead()).thenReturn(Boolean.TRUE);
        final Character aliveTarget = mock(Character.class);
        // Paralyzing a dead char is invalid
        assertThat(immobilizationEffect.appliesTo(caster, deadTarget)).isFalse();
        // Paralyzing a live char is valid
        assertThat(immobilizationEffect.appliesTo(caster, aliveTarget)).isTrue();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);
        // Should always false, no matter what
        assertThat(immobilizationEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);
        // Should do nothing....
        try {
            immobilizationEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (final InvalidTargetException e) {
            // this is ok
        }
    }

}
