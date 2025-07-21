

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

public class RecoverMobilityEffectTest {

	private Effect recoverMobilityEffect;

	@Before
	public void setUp() throws Exception {
		recoverMobilityEffect = new RecoverMobilityEffect();
	}

	@Test
	public void testApplyCharacterCharacter() {
		final Character caster = mock(Character.class);
		final Character target = mock(Character.class);

		recoverMobilityEffect.apply(caster, target);

		verify(target).setImmobilized(false);
		verify(target).setParalyzed(false);
	}

	@Test
	public void testAppliesToCharacterCharacter() {
		final Character caster = mock(Character.class);

		// An immobilized char is valid.
		final Character immobilizedTarget = mock(Character.class);
		when(immobilizedTarget.isImmobilized()).thenReturn(Boolean.TRUE);
		assertTrue(recoverMobilityEffect.appliesTo(caster, immobilizedTarget));

		// A paralyzed char is valid.
		final Character palayzedTarget = mock(Character.class);
		when(palayzedTarget.isParalyzed()).thenReturn(Boolean.TRUE);
		assertTrue(recoverMobilityEffect.appliesTo(caster, palayzedTarget));

		// A non-paralyzed, non/immobilized char is invalid.
		final Character normalTarget = mock(Character.class);
		assertFalse(recoverMobilityEffect.appliesTo(caster, normalTarget));

		// A paralyzed dead char is invalid.
		final Character palayzedDeadTarget = mock(Character.class);
		when(palayzedDeadTarget.isParalyzed()).thenReturn(Boolean.TRUE);
		when(palayzedDeadTarget.isDead()).thenReturn(Boolean.TRUE);
		assertFalse(recoverMobilityEffect.appliesTo(caster, palayzedDeadTarget));

		// A immobilized dead char is invalid.
		final Character immobilizedDeadTarget = mock(Character.class);
		when(immobilizedDeadTarget.isImmobilized()).thenReturn(Boolean.TRUE);
		when(immobilizedDeadTarget.isDead()).thenReturn(Boolean.TRUE);
		assertFalse(recoverMobilityEffect.appliesTo(caster, immobilizedDeadTarget));
	}

	@Test
	public void testAppliesToCharacterWorldObject() {
		final WorldObject obj = mock(WorldObject.class);
		final Character caster = mock(Character.class);

		// Should always false, no matter what
		assertFalse(recoverMobilityEffect.appliesTo(caster, obj));
	}

	@Test
	public void testApplyCharacterWorldObject() {
		final WorldObject obj = mock(WorldObject.class);
		final Character caster = mock(Character.class);

		// Should do nothing....
		try {
			recoverMobilityEffect.apply(caster, obj);
			fail("Applying an effect for characters to a world object didn't fail.");
		} catch (final InvalidTargetException e) {
			// this is ok
		}
	}

}
