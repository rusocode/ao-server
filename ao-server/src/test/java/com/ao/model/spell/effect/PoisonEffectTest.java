

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

public class PoisonEffectTest {

	private Effect poisonEffect;

	@Before
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

		// Paralyzing a dead char is invalid.
		assertFalse(poisonEffect.appliesTo(caster, deadTarget));

		/// Paralyzing an alive char is valid.
		assertTrue(poisonEffect.appliesTo(caster, aliveTarget));
	}

	@Test
	public void testAppliesToCharacterWorldObject() {
		final WorldObject obj = mock(WorldObject.class);
		final Character caster = mock(Character.class);

		// Should always false, no matter what
		assertFalse(poisonEffect.appliesTo(caster, obj));
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
			// this is ok
		}
	}

}
