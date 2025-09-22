package com.ao.data.dao.ini;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.ObjectType;
import com.ao.model.worldobject.properties.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ObjectPropertiesDAOIniTest {

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

    protected ObjectDAOIni worldObjectPropertiesDAOIni;

    @BeforeEach
    public void setUp() throws Exception {
        worldObjectPropertiesDAOIni = new ObjectDAOIni("data/objects.dat", TEST_ITEMS_PER_ROW);
    }

    @Test
    public void testLoad() {
        try {
            worldObjectPropertiesDAOIni.load();
        } catch (DAOException e) {
            fail("Loading of objects failed with message: " + e.getMessage());
        }

        // Check some items to make sure they loaded properly...
        ObjectProperties yellowPotion = worldObjectPropertiesDAOIni.getObjectProperties(YELLOW_POTION_INDEX);
        assertThat(yellowPotion).isInstanceOf(TemporalStatModifyingItemProperties.class);
        assertThat(yellowPotion.getType()).isEqualTo(ObjectType.DEXTERITY_POTION);

        ObjectProperties bluePotion = worldObjectPropertiesDAOIni.getObjectProperties(BLUE_POTION_INDEX);
        assertThat(bluePotion).isInstanceOf(StatModifyingItemProperties.class);
        assertThat(bluePotion.getType()).isEqualTo(ObjectType.MANA_POTION);

        ObjectProperties redPotion = worldObjectPropertiesDAOIni.getObjectProperties(RED_POTION_INDEX);
        assertThat(redPotion).isInstanceOf(StatModifyingItemProperties.class);
        assertThat(redPotion.getType()).isEqualTo(ObjectType.HP_POTION);

        ObjectProperties greenPotion = worldObjectPropertiesDAOIni.getObjectProperties(GREEN_POTION_INDEX);
        assertThat(greenPotion).isInstanceOf(TemporalStatModifyingItemProperties.class);
        assertThat(greenPotion.getType()).isEqualTo(ObjectType.STRENGTH_POTION);

        ObjectProperties violetPotion = worldObjectPropertiesDAOIni.getObjectProperties(VIOLET_POTION_INDEX);
        assertThat(violetPotion).isInstanceOf(ItemProperties.class);
        assertThat(violetPotion.getType()).isEqualTo(ObjectType.POISON_POTION);

        ObjectProperties blackPotion = worldObjectPropertiesDAOIni.getObjectProperties(BLACK_POTION_INDEX);
        assertThat(blackPotion).isInstanceOf(ItemProperties.class);
        assertThat(blackPotion.getType()).isEqualTo(ObjectType.DEATH_POTION);

        ObjectProperties sign = worldObjectPropertiesDAOIni.getObjectProperties(SIGN_INDEX);
        assertThat(sign).isInstanceOf(SignProperties.class);
        assertThat(sign.getType()).isEqualTo(ObjectType.SIGN);

        ObjectProperties ullathorpeForum = worldObjectPropertiesDAOIni.getObjectProperties(ULLATHORPE_FORUM_INDEX);
        assertThat(ullathorpeForum).isInstanceOf(ForumProperties.class);
        assertThat(ullathorpeForum.getType()).isEqualTo(ObjectType.FORUM);

        ObjectProperties backpack = worldObjectPropertiesDAOIni.getObjectProperties(BACKPACK_INDEX);
        assertThat(backpack).isInstanceOf(BackpackProperties.class);
        assertThat(backpack.getType()).isEqualTo(ObjectType.BACKPACK);

        ObjectProperties mineral = worldObjectPropertiesDAOIni.getObjectProperties(MINERAL_INDEX);
        assertThat(mineral).isInstanceOf(MineralProperties.class);
        assertThat(mineral.getType()).isEqualTo(ObjectType.MINERAL);

        // TODO Keep doing this with other object types. Also check some other attributes are properly loaded...
    }

}
