package com.ao.data.dao.ini;

import com.ao.data.dao.exception.DAOException;
import com.ao.model.worldobject.WorldObjectType;
import com.ao.model.worldobject.properties.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

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


    private static final String TEST_OBJ_DAT = "src/test/resources/obj.dat";
    private static final int TEST_ITEMS_PER_ROW = 5;

    protected WorldObjectPropertiesDAOIni dao;

    @Before
    public void setUp() throws Exception {
        dao = new WorldObjectPropertiesDAOIni(TEST_OBJ_DAT, TEST_ITEMS_PER_ROW);
    }

    @Test
    public void testRetrieveAll() {
        try {
            dao.loadAll();
        } catch (final DAOException e) {
            fail("Loading of objects failed with message " + e.getMessage());
        }

        // Check some items to make sure they loaded properly...
        final WorldObjectProperties yellowPotion = dao.getWorldObjectProperties(YELLOW_POTION_INDEX);
        assertThat(yellowPotion, instanceOf(TemporalStatModifyingItemProperties.class));
        assertEquals(WorldObjectType.DEXTERITY_POTION, yellowPotion.getType());

        final WorldObjectProperties bluePotion = dao.getWorldObjectProperties(BLUE_POTION_INDEX);
        assertThat(bluePotion, instanceOf(StatModifyingItemProperties.class));
        assertEquals(WorldObjectType.MANA_POTION, bluePotion.getType());

        final WorldObjectProperties redPotion = dao.getWorldObjectProperties(RED_POTION_INDEX);
        assertThat(redPotion, instanceOf(StatModifyingItemProperties.class));
        assertEquals(WorldObjectType.HP_POTION, redPotion.getType());

        final WorldObjectProperties greenPotion = dao.getWorldObjectProperties(GREEN_POTION_INDEX);
        assertThat(greenPotion, instanceOf(TemporalStatModifyingItemProperties.class));
        assertEquals(WorldObjectType.STRENGTH_POTION, greenPotion.getType());

        final WorldObjectProperties violetPotion = dao.getWorldObjectProperties(VIOLET_POTION_INDEX);
        assertThat(violetPotion, instanceOf(ItemProperties.class));
        assertEquals(WorldObjectType.POISON_POTION, violetPotion.getType());

        final WorldObjectProperties blackPotion = dao.getWorldObjectProperties(BLACK_POTION_INDEX);
        assertThat(blackPotion, instanceOf(ItemProperties.class));
        assertEquals(WorldObjectType.DEATH_POTION, blackPotion.getType());

        final WorldObjectProperties sign = dao.getWorldObjectProperties(SIGN_INDEX);
        assertThat(sign, instanceOf(SignProperties.class));
        assertEquals(WorldObjectType.SIGN, sign.getType());

        final WorldObjectProperties ullathorpeForum = dao.getWorldObjectProperties(ULLATHORPE_FORUM_INDEX);
        assertThat(ullathorpeForum, instanceOf(ForumProperties.class));
        assertEquals(WorldObjectType.FORUM, ullathorpeForum.getType());

        final WorldObjectProperties backpack = dao.getWorldObjectProperties(BACKPACK_INDEX);
        assertThat(backpack, instanceOf(BackpackProperties.class));
        assertEquals(WorldObjectType.BACKPACK, backpack.getType());

        final WorldObjectProperties mineral = dao.getWorldObjectProperties(MINERAL_INDEX);
        assertThat(mineral, instanceOf(MineralProperties.class));
        assertEquals(WorldObjectType.MINERAL, mineral.getType());

        // TODO Keep doing this with other object types. Also check some other attributes are properly loaded...
    }

}
