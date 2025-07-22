package com.ao.model.spell.effect;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.character.NPCCharacter;
import com.ao.model.character.UserCharacter;
import com.ao.model.worldobject.WorldObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InvisibilityEffectTest {

    private Effect invisibilityEffect;

    @Before
    public void setUp() throws Exception {
        invisibilityEffect = new InvisibilityEffect();
    }

    @Test
    public void testApplyCharacterCharacter() {
        final Character caster = mock(Character.class);
        final Character target = mock(Character.class);
        invisibilityEffect.apply(caster, target);
        verify(target).setInvisible(true);
    }

    @Test
    public void testAppliesToCharacterCharacter() {
        final Character caster = mock(Character.class);
        final UserCharacter deadUserTarget = mock(UserCharacter.class);
        when(deadUserTarget.isDead()).thenReturn(Boolean.TRUE);
        final UserCharacter aliveUserTarget = mock(UserCharacter.class);
        final NPCCharacter target = mock(NPCCharacter.class);

        // Test invalid target
        assertFalse(invisibilityEffect.appliesTo(caster, target));

        // Test dead target
        assertFalse(invisibilityEffect.appliesTo(caster, deadUserTarget));

        // Test alive target
        assertTrue(invisibilityEffect.appliesTo(caster, aliveUserTarget));
    }

    @Test
    public void testAppliesToCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);
        assertFalse(invisibilityEffect.appliesTo(caster, obj));
    }

    @Test
    public void testApplyCharacterWorldObject() {
        final WorldObject obj = mock(WorldObject.class);
        final Character caster = mock(Character.class);
        // This should do fail
        try {
            invisibilityEffect.apply(caster, obj);
            fail("Applying an effect for characters to a world object didn't fail.");
        } catch (final InvalidTargetException e) {
            // this is ok
        }
    }

}
