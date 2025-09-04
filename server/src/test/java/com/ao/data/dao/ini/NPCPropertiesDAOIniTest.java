package com.ao.data.dao.ini;

import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.NPCType;
import com.ao.model.character.npc.properties.CreatureNPCProperties;
import com.ao.model.character.npc.properties.NPCProperties;
import com.ao.model.map.City;
import com.ao.model.worldobject.AbstractItem;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.ao.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for NPCPropertiesDAOIni.
 */

public class NPCPropertiesDAOIniTest {

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
        City mockCity = new City(1, (byte) 50, (byte) 50);
        when(mapService.getCity(anyByte())).thenReturn(mockCity);

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

        NPCProperties zombie = npcProperties[COMMON_NPC_INDEX - 1]; // Le resta 1 ya que el índice comienza en 1 y el array en 0
        assertThat(zombie).isInstanceOf(CreatureNPCProperties.class);
        assertThat(zombie.getType()).isEqualTo(NPCType.COMMON);

        /* NPCProperties dragon = npcProperties[DRAGON_NPC_INDEX - 1];
        assertThat(dragon).isInstanceOf(CreatureNPCProperties.class);
        assertThat(dragon.getType()).isEqualTo(NPCType.DRAGON);

        NPCProperties trainer = npcProperties[TRAINER_NPC_INDEX - 1];
        assertThat(trainer).isInstanceOf(TrainerNPCProperties.class);
        assertThat(trainer.getType()).isEqualTo(NPCType.TRAINER);

        NPCProperties governor = npcProperties[GOVERNOR_NPC_INDEX - 1];
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
