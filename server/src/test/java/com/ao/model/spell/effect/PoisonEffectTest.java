package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;
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
        final Character caster = mock(Character.class);
        final Character target = mock(Character.class);

        poisonEffect.apply(caster, target);
        verify(target).setPoisoned(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        final Character caster = mock(Character.class);
        final Character deadTarget = mock(Character.class);
        final Character aliveTarget = mock(Character.class);
        when(deadTarget.isDead()).thenReturn(Boolean.TRUE);

        // Paralyzing a dead char is invalid
        assertThat(poisonEffect.appliesTo(caster, deadTarget)).isFalse();

        // Paralyzing a live char is valid
        assertThat(poisonEffect.appliesTo(caster, aliveTarget)).isTrue();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);

        // Should always false, no matter what
        assertThat(poisonEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);

        // Should do nothing....
        try {
            poisonEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (final InvalidTargetException e) {
            // This is ok
        }
    }

}
