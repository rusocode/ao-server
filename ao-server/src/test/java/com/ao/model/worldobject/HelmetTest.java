package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class HelmetTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Helmet helmet1;
    private Helmet helmet2;

    @Before
    public void setUp() throws Exception {
        final DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.HELMET, 1, "Viking Helmet", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        helmet1 = new Helmet(props1, 5);

        final DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.HELMET, 1, "Viking Helmet", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        helmet2 = new Helmet(props2, 1);

        object = helmet1;
        ammount = 5;
        objectProps = props1;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        final Helmet clone = (Helmet) helmet1.clone();

        // Make sure all fields match
        assertEquals(helmet1.amount, clone.amount);
        assertEquals(helmet1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(helmet1, clone);


        final Helmet clone2 = (Helmet) helmet2.clone();

        // Make sure all fields match
        assertEquals(helmet2.amount, clone2.amount);
        assertEquals(helmet2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(helmet2, clone2);
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        helmet1.use(character);
        helmet2.use(character);

        verifyNoInteractions(character);
    }

}
