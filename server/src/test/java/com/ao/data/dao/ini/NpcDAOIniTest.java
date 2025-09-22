package com.ao.data.dao.ini;

import com.ao.data.dao.ObjectDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.NpcType;
import com.ao.model.character.npc.properties.CreatureNpc;
import com.ao.model.character.npc.properties.Npc;
import com.ao.model.map.City;
import com.ao.model.worldobject.AbstractItem;
import com.ao.model.worldobject.factory.ObjectFactory;
import com.ao.model.worldobject.properties.ObjectProperties;
import com.ao.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for NpcDAOIni.
 */

public class NpcDAOIniTest {

    private static final int COMMON_NPC_INDEX = 158;
    private static final int RESUCITATOR_NPC_INDEX = 6;
    private static final int ROYAL_GUARD_NPC_INDEX = 118;
    private static final int TRAINER_NPC_INDEX = 10;
    private static final int BANKER_NPC_INDEX = 13;
    private static final int NOBLE_NPC_INDEX = 8;
    private static final int DRAGON_NPC_INDEX = 198;
    private static final int GAMBLER_NPC_INDEX = 14;
    private static final int CHAOS_GUARD_NPC_INDEX = 124;
    private static final int NEWBIE_RESUCITATOR_NPC_INDEX = 7;
    private static final int GOVERNOR_NPC_INDEX = 39;

    private NpcDAOIni npcDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        ObjectProperties woProperties = mock(ObjectProperties.class);
        AbstractItem item = mock(AbstractItem.class);

        ObjectDAO woDao = mock(ObjectDAO.class);
        when(woDao.getObjectProperties(anyInt())).thenReturn(woProperties);

        ObjectFactory woFactory = mock(ObjectFactory.class);
        when(woFactory.getObject(eq(woProperties), anyInt())).thenReturn(item);

        MapService mapService = mock(MapService.class);
        City mockCity = new City(1, (byte) 50, (byte) 50);
        when(mapService.getCity(anyByte())).thenReturn(mockCity);

        npcDAOIni = new NpcDAOIni("data/npcs.dat", woDao, woFactory, mapService);
    }

    @Test
    public void testLoad() {
        Npc[] npcProperties;
        try {
            npcProperties = npcDAOIni.load();
        } catch (DAOException e) {
            fail("Loading of npcs failed with message " + e.getMessage());
            return;
        }

        Npc zombie = npcProperties[COMMON_NPC_INDEX - 1]; // Le resta 1 ya que el índice comienza en 1 y el array en 0
        assertThat(zombie).isInstanceOf(CreatureNpc.class);
        assertThat(zombie.getType()).isEqualTo(NpcType.COMMON);

        /* NpcProperties dragon = npcProperties[DRAGON_NPC_INDEX - 1];
        assertThat(dragon).isInstanceOf(CreatureNpcProperties.class);
        assertThat(dragon.getType()).isEqualTo(NpcType.DRAGON);

        NpcProperties trainer = npcProperties[TRAINER_NPC_INDEX - 1];
        assertThat(trainer).isInstanceOf(TrainerNpcProperties.class);
        assertThat(trainer.getType()).isEqualTo(NpcType.TRAINER);

        NpcProperties governor = npcProperties[GOVERNOR_NPC_INDEX - 1];
        assertThat(governor).isInstanceOf(GovernorNPCProperties.class);
        assertThat(governor.getType()).isEqualTo(NPCType.GOVERNOR);

        NPCProperties royalGuard = npcProperties[ROYAL_GUARD_NPC_INDEX - 1];
        assertThat(royalGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(royalGuard.getType()).isEqualTo(NPCType.ROYAL_GUARD);

        NPCProperties chaosGuard = npcProperties[CHAOS_GUARD_NPC_INDEX - 1];
        assertThat(chaosGuard).isInstanceOf(GuardNPCProperties.class);
        assertThat(chaosGuard.getType()).isEqualTo(NPCType.CHAOS_GUARD);

        NPCProperties newbieResucitator = npcProperties[NEWBIE_RESUCITATOR_NPC_INDEX - 1];
        assertThat(newbieResucitator).isInstanceOf(NPCProperties.class);
        assertThat(newbieResucitator.getType()).isEqualTo(NPCType.NEWBIE_RESUCITATOR);

        NPCProperties resucitator = npcProperties[RESUCITATOR_NPC_INDEX - 1];
        assertThat(resucitator).isInstanceOf(NPCProperties.class);
        assertThat(resucitator.getType()).isEqualTo(NPCType.RESUCITATOR);

        NPCProperties gambler = npcProperties[GAMBLER_NPC_INDEX - 1];
        assertThat(gambler).isInstanceOf(NPCProperties.class);
        assertThat(gambler.getType()).isEqualTo(NPCType.GAMBLER);

        NPCProperties banker = npcProperties[BANKER_NPC_INDEX - 1];
        assertThat(banker).isInstanceOf(NPCProperties.class);
        assertThat(banker.getType()).isEqualTo(NPCType.BANKER);

        NPCProperties noble = npcProperties[NOBLE_NPC_INDEX - 1];
        assertThat(noble).isInstanceOf(NobleNPCProperties.class);
        assertThat(noble.getType()).isEqualTo(NPCType.NOBLE); */

    }

}
