package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.character.NpcCharacter;
import com.ao.model.character.UserCharacter;
import com.ao.model.object.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class InvisibilityEffectTest {

    private Effect invisibilityEffect;

    @BeforeEach
    public void setUp() throws Exception {
        invisibilityEffect = new InvisibilityEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character caster = mock(Character.class);
        Character target = mock(Character.class);
        invisibilityEffect.apply(caster, target);
        verify(target).setInvisible(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character caster = mock(Character.class);
        UserCharacter deadUserTarget = mock(UserCharacter.class);
        when(deadUserTarget.isDead()).thenReturn(Boolean.TRUE);
        UserCharacter aliveUserTarget = mock(UserCharacter.class);
        NpcCharacter target = mock(NpcCharacter.class);

        // Test invalid target
        assertThat(invisibilityEffect.appliesTo(caster, target)).isFalse();

        // Test dead target
        assertThat(invisibilityEffect.appliesTo(caster, deadUserTarget)).isFalse();

        // Test alive target
        assertThat(invisibilityEffect.appliesTo(caster, aliveUserTarget)).isTrue();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);
        assertThat(invisibilityEffect.appliesTo(caster, obj)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);
        // This should do fail
        try {
            invisibilityEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // this is ok
        }
    }

}
