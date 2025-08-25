package com.ao.data.dao.ini;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.WorldObjectType;
import com.ao.model.worldobject.properties.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class WorldObjectPropertiesDAOIniTest {

    private static final int YELLOW_POTION_INDEX = 36;
    private static final int BLUE_POTION_INDEX = 37;
    private static final int RED_POTION_INDEX = 38;
    private static final int GREEN_POTION_INDEX = 39;
    private static final int VIOLET_POTION_INDEX = 166;
    private static final int BLACK_POTION_INDEX = 645;

    private static final int SIGN_INDEX = 13;
    private static final int ULLATHORPE_FORUM_INDEX = 34;
    private static final int BACKPACK_INDEX = 866;
    private static final int MINERAL_INDEX = 192;

    private static final int TEST_ITEMS_PER_ROW = 5;

    protected WorldObjectPropertiesDAOIni worldObjectPropertiesDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        worldObjectPropertiesDAOIni = new WorldObjectPropertiesDAOIni("dat/objects.dat", TEST_ITEMS_PER_ROW);
    }

    @Test
    public void testLoad() {
        try {
            worldObjectPropertiesDAOIni.load();
        } catch (DAOException e) {
            fail("Loading of objects failed with message: " + e.getMessage());
        }

        // Check some items to make sure they loaded properly...
        WorldObjectProperties yellowPotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(YELLOW_POTION_INDEX);
        assertThat(yellowPotion).isInstanceOf(TemporalStatModifyingItemProperties.class);
        assertThat(yellowPotion.getType()).isEqualTo(WorldObjectType.DEXTERITY_POTION);

        WorldObjectProperties bluePotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(BLUE_POTION_INDEX);
        assertThat(bluePotion).isInstanceOf(StatModifyingItemProperties.class);
        assertThat(bluePotion.getType()).isEqualTo(WorldObjectType.MANA_POTION);

        WorldObjectProperties redPotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(RED_POTION_INDEX);
        assertThat(redPotion).isInstanceOf(StatModifyingItemProperties.class);
        assertThat(redPotion.getType()).isEqualTo(WorldObjectType.HP_POTION);

        WorldObjectProperties greenPotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(GREEN_POTION_INDEX);
        assertThat(greenPotion).isInstanceOf(TemporalStatModifyingItemProperties.class);
        assertThat(greenPotion.getType()).isEqualTo(WorldObjectType.STRENGTH_POTION);

        WorldObjectProperties violetPotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(VIOLET_POTION_INDEX);
        assertThat(violetPotion).isInstanceOf(ItemProperties.class);
        assertThat(violetPotion.getType()).isEqualTo(WorldObjectType.POISON_POTION);

        WorldObjectProperties blackPotion = worldObjectPropertiesDAOIni.getWorldObjectProperties(BLACK_POTION_INDEX);
        assertThat(blackPotion).isInstanceOf(ItemProperties.class);
        assertThat(blackPotion.getType()).isEqualTo(WorldObjectType.DEATH_POTION);

        WorldObjectProperties sign = worldObjectPropertiesDAOIni.getWorldObjectProperties(SIGN_INDEX);
        assertThat(sign).isInstanceOf(SignProperties.class);
        assertThat(sign.getType()).isEqualTo(WorldObjectType.SIGN);

        WorldObjectProperties ullathorpeForum = worldObjectPropertiesDAOIni.getWorldObjectProperties(ULLATHORPE_FORUM_INDEX);
        assertThat(ullathorpeForum).isInstanceOf(ForumProperties.class);
        assertThat(ullathorpeForum.getType()).isEqualTo(WorldObjectType.FORUM);

        WorldObjectProperties backpack = worldObjectPropertiesDAOIni.getWorldObjectProperties(BACKPACK_INDEX);
        assertThat(backpack).isInstanceOf(BackpackProperties.class);
        assertThat(backpack.getType()).isEqualTo(WorldObjectType.BACKPACK);

        WorldObjectProperties mineral = worldObjectPropertiesDAOIni.getWorldObjectProperties(MINERAL_INDEX);
        assertThat(mineral).isInstanceOf(MineralProperties.class);
        assertThat(mineral.getType()).isEqualTo(WorldObjectType.MINERAL);

        // TODO Keep doing this with other object types. Also check some other attributes are properly loaded...
    }

}
