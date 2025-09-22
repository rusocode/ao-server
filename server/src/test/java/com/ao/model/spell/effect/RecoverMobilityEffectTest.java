package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class RecoverMobilityEffectTest {

    private Effect recoverMobilityEffect;

    @BeforeEach
    public void setUp() throws Exception {
        recoverMobilityEffect = new RecoverMobilityEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character caster = mock(Character.class);
        Character target = mock(Character.class);

        recoverMobilityEffect.apply(caster, target);

        verify(target).setImmobilized(false);
        verify(target).setParalyzed(false);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character caster = mock(Character.class);

        // An immobilized char is valid
        Character immobilizedTarget = mock(Character.class);
        when(immobilizedTarget.isImmobilized()).thenReturn(Boolean.TRUE);
        assertThat(recoverMobilityEffect.appliesTo(caster, immobilizedTarget)).isTrue();

        // A paralyzed char is valid
        Character palayzedTarget = mock(Character.class);
        when(palayzedTarget.isParalyzed()).thenReturn(Boolean.TRUE);
        assertThat(recoverMobilityEffect.appliesTo(caster, palayzedTarget)).isTrue();

        // A non-paralyzed, non/immobilized char is invalid
        Character normalTarget = mock(Character.class);
        assertThat(recoverMobilityEffect.appliesTo(caster, normalTarget)).isFalse();

        // A paralyzed dead char is invalid
        Character palayzedDeadTarget = mock(Character.class);
        when(palayzedDeadTarget.isParalyzed()).thenReturn(Boolean.TRUE);
        when(palayzedDeadTarget.isDead()).thenReturn(Boolean.TRUE);
        assertThat(recoverMobilityEffect.appliesTo(caster, palayzedDeadTarget)).isFalse();

        // An immobilized dead char is invalid
        Character immobilizedDeadTarget = mock(Character.class);
        when(immobilizedDeadTarget.isImmobilized()).thenReturn(Boolean.TRUE);
        when(immobilizedDeadTarget.isDead()).thenReturn(Boolean.TRUE);
        assertThat(recoverMobilityEffect.appliesTo(caster, immobilizedDeadTarget)).isFalse();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);

        // Should always false, no matter what
        assertThat(recoverMobilityEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);

        // Should do nothing....
        try {
            recoverMobilityEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // this is ok
        }
    }

}
