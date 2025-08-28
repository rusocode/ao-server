package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.character.UserCharacter;
import com.ao.model.worldobject.WorldObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DumbEffectTest {

    private DumbEffect dumbEffect;

    @BeforeEach
    public void setUp() throws Exception {
        dumbEffect = new DumbEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        Character target = mock(Character.class);
        Character caster = mock(Character.class);

        dumbEffect.apply(caster, target);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character target = mock(Character.class);
        Character caster = mock(Character.class);

        assertThat(dumbEffect.appliesTo(caster, target)).isFalse();

        Character deadUserTarget = mock(UserCharacter.class);
        when(deadUserTarget.isDead()).thenReturn(Boolean.TRUE);
        Character aliveUserTarget = mock(UserCharacter.class);

        assertThat(dumbEffect.appliesTo(caster, aliveUserTarget)).isTrue();
        assertThat(dumbEffect.appliesTo(caster, deadUserTarget)).isFalse();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Character caster = mock(Character.class);
        WorldObject target = mock(WorldObject.class);

        assertThat(dumbEffect.appliesTo(caster, target)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        WorldObject obj = mock(WorldObject.class);
        Character caster = mock(Character.class);
        // Should do nothing....
        try {
            dumbEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // This is ok
        }
    }

}
