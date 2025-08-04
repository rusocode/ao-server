package com.ao.model.character.archetype;

import com.ao.ioc.ArchetypeLocator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserArchetypeTest {

    @Test
    public void testGetArchetype() {
        assertThat(UserArchetype.ASSASIN.getArchetype()).isInstanceOf(AssasinArchetype.class);
        assertThat(UserArchetype.BANDIT.getArchetype()).isInstanceOf(BanditArchetype.class);
        assertThat(UserArchetype.BARD.getArchetype()).isInstanceOf(BardArchetype.class);
        assertThat(UserArchetype.CLERIC.getArchetype()).isInstanceOf(ClericArchetype.class);
        assertThat(UserArchetype.DRUID.getArchetype()).isInstanceOf(DruidArchetype.class);
        assertThat(UserArchetype.HUNTER.getArchetype()).isInstanceOf(HunterArchetype.class);
        assertThat(UserArchetype.MAGE.getArchetype()).isInstanceOf(MageArchetype.class);
        assertThat(UserArchetype.PALADIN.getArchetype()).isInstanceOf(PaladinArchetype.class);
        assertThat(UserArchetype.PIRATE.getArchetype()).isInstanceOf(PirateArchetype.class);
        assertThat(UserArchetype.THIEF.getArchetype()).isInstanceOf(ThiefArchetype.class);
        assertThat(UserArchetype.WARRIOR.getArchetype()).isInstanceOf(WarriorArchetype.class);
        assertThat(UserArchetype.WORKER.getArchetype()).isInstanceOf(WorkerArchetype.class);
    }

    @Test
    public void testValueOf() {
        final Archetype archetype = ArchetypeLocator.getArchetype(AssasinArchetype.class); // Es necesario declararlo como final?
        assertThat(UserArchetype.ASSASIN).isSameAs(UserArchetype.valueOf(archetype));
    }

}
