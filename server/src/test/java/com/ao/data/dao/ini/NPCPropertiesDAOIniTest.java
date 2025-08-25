package com.ao.data.dao.ini;

import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.NPCType;
import com.ao.model.character.npc.properties.*;
import com.ao.model.worldobject.AbstractItem;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for NPCPropertiesDAOIni.
 */

public class NPCPropertiesDAOIniTest {

    private static final int COMMON_NPC_INDEX = 503;
    private static final int DRAGON_NPC_INDEX = 541;
    private static final int TRAINER_NPC_INDEX = 59;
    private static final int GOVERNOR_NPC_INDEX = 12;
    private static final int ROYAL_GUARD_NPC_INDEX = 5;
    private static final int CHAOS_GUARD_NPC_INDEX = 126;
    private static final int NEWBIE_RESUCITATOR_NPC_INDEX = 118;
    private static final int RESUCITATOR_NPC_INDEX = 4;
    private static final int GAMBLER_NPC_INDEX = 103;
    private static final int BANKER_NPC_INDEX = 23;
    private static final int NOBLE_NPC_INDEX = 71;

    private static final String TEST_NPCS_DAT = "NPCs.dat";

    private NPCPropertiesDAOIni dao;

    @BeforeEach
    public void setUp() throws Exception {
        final WorldObjectProperties woProperties = mock(WorldObjectProperties.class);
        final AbstractItem item = mock(AbstractItem.class);

        final WorldObjectPropertiesDAO woDao = mock(WorldObjectPropertiesDAO.class);
        when(woDao.getWorldObjectProperties(anyInt())).thenReturn(woProperties);

        final WorldObjectFactory woFactory = mock(WorldObjectFactory.class);
        when(woFactory.getWorldObject(eq(woProperties), anyInt())).thenReturn(item);

        dao = new NPCPropertiesDAOIni(TEST_NPCS_DAT, woDao, woFactory);
    }

    @Test
    public void testLoad() {
        final NPCProperties[] npcProperties;
        try {
            npcProperties = dao.load();
        } catch (final DAOException e) {
            fail("Loading of npcs failed with message " + e.getMessage());
            return;
        }

        final NPCProperties snake = npcProperties[COMMON_NPC_INDEX];
        assertThat(snake).isInstanceOf(CreatureNPCProperties.class);
        assertThat(snake.getType()).isEqualTo(NPCType.COMMON);

        final NPCProperties dragon = npcProperties[DRAGON_NPC_INDEX];
        assertThat(dragon).isInstanceOf(CreatureNPCProperties.class);
        assertThat(dragon.getType()).isEqualTo(NPCType.DRAGON);

        final NPCProperties trainer = npcProperties[TRAINER_NPC_INDEX];
        assertThat(trainer).isInstanceOf(TrainerNPCProperties.class);
        assertThat(trainer.getType()).isEqualTo(NPCType.TRAINER);

        final NPCProperties governor = npcProperties[GOVERNOR_NPC_INDEX];
        assertThat(governor).isInstanceOf(GovernorNPCProperties.class);
        assertThat(governor.getType()).isEqualTo(NPCType.GOVERNOR);

        final NPCProperties royalGuard = npcProperties[ROYAL_GUARD_NPC_INDEX];
        assertThat(royalGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(royalGuard.getType()).isEqualTo(NPCType.ROYAL_GUARD);

        final NPCProperties chaosGuard = npcProperties[CHAOS_GUARD_NPC_INDEX];
        assertThat(chaosGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(chaosGuard.getType()).isEqualTo(NPCType.CHAOS_GUARD);

        final NPCProperties newbieResucitator = npcProperties[NEWBIE_RESUCITATOR_NPC_INDEX];
        assertThat(newbieResucitator).isInstanceOf(NPCProperties.class);
        assertThat(newbieResucitator.getType()).isEqualTo(NPCType.NEWBIE_RESUCITATOR);

        final NPCProperties resucitator = npcProperties[RESUCITATOR_NPC_INDEX];
        assertThat(resucitator).isInstanceOf(NPCProperties.class);
        assertThat(resucitator.getType()).isEqualTo(NPCType.RESUCITATOR);

        final NPCProperties gambler = npcProperties[GAMBLER_NPC_INDEX];
        assertThat(gambler).isInstanceOf(NPCProperties.class);
        assertThat(gambler.getType()).isEqualTo(NPCType.GAMBLER);

        final NPCProperties banker = npcProperties[BANKER_NPC_INDEX];
        assertThat(banker).isInstanceOf(NPCProperties.class);
        assertThat(banker.getType()).isEqualTo(NPCType.BANKER);

        final NPCProperties noble = npcProperties[NOBLE_NPC_INDEX];
        assertThat(noble).isInstanceOf(NobleNPCProperties.class);
        assertThat(noble.getType()).isEqualTo(NPCType.NOBLE);

    }

}
