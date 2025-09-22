package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.object.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HitPointsEffectTest {

    private static final int MIN_POINTS = 5;
    private static final int MAX_POINTS = 10;

    private HitPointsEffect hpEffect1;
    private HitPointsEffect hpEffect2;

    @BeforeEach
    public void setUp() throws Exception {
        hpEffect1 = new HitPointsEffect(MIN_POINTS, MAX_POINTS);
        hpEffect2 = new HitPointsEffect(-MAX_POINTS, -MIN_POINTS);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        Character deadTarget = mock(Character.class);
        when(deadTarget.isDead()).thenReturn(Boolean.TRUE);
        Character aliveTarget = mock(Character.class);
        Character caster = mock(Character.class);

        assertThat(hpEffect1.appliesTo(caster, aliveTarget)).isTrue();
        assertThat(hpEffect2.appliesTo(caster, aliveTarget)).isTrue();
        assertThat(hpEffect1.appliesTo(caster, deadTarget)).isFalse();
        assertThat(hpEffect2.appliesTo(caster, deadTarget)).isFalse();
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        Character caster = mock(Character.class);
        Object target = mock(Object.class);

        assertThat(hpEffect1.appliesTo(caster, target)).isFalse();
        assertThat(hpEffect2.appliesTo(caster, target)).isFalse();
    }

    @Test
    public void testApplyCharacterWorldObject() {
        Object obj = mock(Object.class);
        Character caster = mock(Character.class);

        // Should do nothing....
        try {
            hpEffect1.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (InvalidTargetException e) {
            // This is ok
        }
    }

}
