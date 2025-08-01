package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class ShieldTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Shield shield1;
    private Shield shield2;

    @Before
    public void setUp() throws Exception {
        final DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.SHIELD, 1, "Turtle Shield", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        shield1 = new Shield(props1, 5);

        final DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.SHIELD, 1, "Turtle Shield", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        shield2 = new Shield(props2, 1);

        object = shield1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        final Shield clone = (Shield) shield1.clone();

        // Make sure all fields match
        assertEquals(shield1.amount, clone.amount);
        assertEquals(shield1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(shield1, clone);


        final Shield clone2 = (Shield) shield2.clone();

        // Make sure all fields match
        assertEquals(shield2.amount, clone2.amount);
        assertEquals(shield2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(shield2, clone);
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        shield1.use(character);
        shield2.use(character);

        verifyNoInteractions(character);
    }

}
