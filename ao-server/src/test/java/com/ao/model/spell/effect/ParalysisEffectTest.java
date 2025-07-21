

package com.ao.model.spell.effect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.ao.exception.InvalidTargetException;
import com.ao.model.character.Character;
import com.ao.model.worldobject.WorldObject;

public class ParalysisEffectTest {

	private Effect paralysisEffect;

	@Before
	public void setUp() throws Exception {
		paralysisEffect = new ParalysisEffect();
	}

	@Test
	public void testApplyCharacterCharacter() {
		final Character caster = mock(Character.class);
		final Character target = mock(Character.class);

		paralysisEffect.apply(caster, target);

		verify(target).setParalyzed(true);
	}

	@Test
	public void testAppliesToCharacterCharacter() {
		final Character caster = mock(Character.class);
		final Character deadTarget = mock(Character.class);
		when(deadTarget.isDead()).thenReturn(Boolean.TRUE);
		final Character aliveTarget = mock(Character.class);

		// Paralyzing a dead char is invalid.
		assertFalse(paralysisEffect.appliesTo(caster, deadTarget));

		// Paralyzing an alive char is valid.
		assertTrue(paralysisEffect.appliesTo(caster, aliveTarget));
	}

	@Test
	public void testAppliesToCharacterWorldObject() {
		final WorldObject obj = mock(WorldObject.class);
		final Character caster = mock(Character.class);

		// Should always false, no matter what
		assertFalse(paralysisEffect.appliesTo(caster, obj));
	}

	@Test
	public void testApplyCharacterWorldObject() {
		final WorldObject obj = mock(WorldObject.class);
		final Character caster = mock(Character.class);

		// Should do nothing....
		try {
			paralysisEffect.apply(caster, obj);
			fail("Applying an effect for characters to a world object didn't fail.");
		} catch (final InvalidTargetException e) {
			// this is ok
		}
	}

}
