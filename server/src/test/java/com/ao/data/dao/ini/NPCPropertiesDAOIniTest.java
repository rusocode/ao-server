package com.ao.data.dao.ini;

import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.NPCType;
import com.ao.model.character.npc.properties.*;
import com.ao.model.worldobject.AbstractItem;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.ao.service.MapService;
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

    private NPCPropertiesDAOIni npcPropertiesDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        WorldObjectProperties woProperties = mock(WorldObjectProperties.class);
        AbstractItem item = mock(AbstractItem.class);

        WorldObjectPropertiesDAO woDao = mock(WorldObjectPropertiesDAO.class);
        when(woDao.getWorldObjectProperties(anyInt())).thenReturn(woProperties);

        WorldObjectFactory woFactory = mock(WorldObjectFactory.class);
        when(woFactory.getWorldObject(eq(woProperties), anyInt())).thenReturn(item);

        MapService mapService = mock(MapService.class);
        byte b = 10;
        when(mapService.getCity(b)).thenReturn(null);

        npcPropertiesDAOIni = new NPCPropertiesDAOIni("data/npcs.dat", woDao, woFactory, mapService);
    }

    @Test
    public void testLoad() {
        NPCProperties[] npcProperties;
        try {
            npcProperties = npcPropertiesDAOIni.load();
        } catch (DAOException e) {
            fail("Loading of npcs failed with message " + e.getMessage());
            return;
        }

        NPCProperties snake = npcProperties[COMMON_NPC_INDEX];
        assertThat(snake).isInstanceOf(CreatureNPCProperties.class);
        assertThat(snake.getType()).isEqualTo(NPCType.COMMON);

        NPCProperties dragon = npcProperties[DRAGON_NPC_INDEX];
        assertThat(dragon).isInstanceOf(CreatureNPCProperties.class);
        assertThat(dragon.getType()).isEqualTo(NPCType.DRAGON);

        NPCProperties trainer = npcProperties[TRAINER_NPC_INDEX];
        assertThat(trainer).isInstanceOf(TrainerNPCProperties.class);
        assertThat(trainer.getType()).isEqualTo(NPCType.TRAINER);

        NPCProperties governor = npcProperties[GOVERNOR_NPC_INDEX];
        assertThat(governor).isInstanceOf(GovernorNPCProperties.class);
        assertThat(governor.getType()).isEqualTo(NPCType.GOVERNOR);

        NPCProperties royalGuard = npcProperties[ROYAL_GUARD_NPC_INDEX];
        assertThat(royalGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(royalGuard.getType()).isEqualTo(NPCType.ROYAL_GUARD);

        NPCProperties chaosGuard = npcProperties[CHAOS_GUARD_NPC_INDEX];
        assertThat(chaosGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(chaosGuard.getType()).isEqualTo(NPCType.CHAOS_GUARD);

        NPCProperties newbieResucitator = npcProperties[NEWBIE_RESUCITATOR_NPC_INDEX];
        assertThat(newbieResucitator).isInstanceOf(NPCProperties.class);
        assertThat(newbieResucitator.getType()).isEqualTo(NPCType.NEWBIE_RESUCITATOR);

        NPCProperties resucitator = npcProperties[RESUCITATOR_NPC_INDEX];
        assertThat(resucitator).isInstanceOf(NPCProperties.class);
        assertThat(resucitator.getType()).isEqualTo(NPCType.RESUCITATOR);

        NPCProperties gambler = npcProperties[GAMBLER_NPC_INDEX];
        assertThat(gambler).isInstanceOf(NPCProperties.class);
        assertThat(gambler.getType()).isEqualTo(NPCType.GAMBLER);

        NPCProperties banker = npcProperties[BANKER_NPC_INDEX];
        assertThat(banker).isInstanceOf(NPCProperties.class);
        assertThat(banker.getType()).isEqualTo(NPCType.BANKER);

        NPCProperties noble = npcProperties[NOBLE_NPC_INDEX];
        assertThat(noble).isInstanceOf(NobleNPCProperties.class);
        assertThat(noble.getType()).isEqualTo(NPCType.NOBLE);

    }

}
