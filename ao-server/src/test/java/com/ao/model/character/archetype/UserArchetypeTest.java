

package com.ao.model.character.archetype;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ao.ioc.ArchetypeLocator;

public class UserArchetypeTest {

	@Test
	public void testGetArchetype() {
		assertThat(UserArchetype.ASSASIN.getArchetype(), instanceOf(AssasinArchetype.class));
		assertThat(UserArchetype.BANDIT.getArchetype(), instanceOf(BanditArchetype.class));
		assertThat(UserArchetype.BARD.getArchetype(), instanceOf(BardArchetype.class));
		assertThat(UserArchetype.CLERIC.getArchetype(), instanceOf(ClericArchetype.class));
		assertThat(UserArchetype.DRUID.getArchetype(), instanceOf(DruidArchetype.class));
		assertThat(UserArchetype.HUNTER.getArchetype(), instanceOf(HunterArchetype.class));
		assertThat(UserArchetype.MAGE.getArchetype(), instanceOf(MageArchetype.class));
		assertThat(UserArchetype.PALADIN.getArchetype(), instanceOf(PaladinArchetype.class));
		assertThat(UserArchetype.PIRATE.getArchetype(), instanceOf(PirateArchetype.class));
		assertThat(UserArchetype.THIEF.getArchetype(), instanceOf(ThiefArchetype.class));
		assertThat(UserArchetype.WARRIOR.getArchetype(), instanceOf(WarriorArchetype.class));
		assertThat(UserArchetype.WORKER.getArchetype(), instanceOf(WorkerArchetype.class));
	}

	@Test
	public void testValueOf() {
		final Archetype arch = ArchetypeLocator.getArchetype(AssasinArchetype.class);

		assertSame(UserArchetype.valueOf(arch), UserArchetype.ASSASIN);
	}

}
