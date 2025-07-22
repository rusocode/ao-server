package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.BoatProperties;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BoatTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_HIT = 1;
    private static final int MAX_HIT = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;
    private static final int USAGE_DIFFICULTY = 3;

    private Boat boat1;
    private Boat boat2;

    @Before
    public void setUp() throws Exception {
        final BoatProperties props1 = new BoatProperties(WorldObjectType.BOAT, 1, "Small Boat", 1, 1, USAGE_DIFFICULTY, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF, MIN_HIT, MAX_HIT);
        boat1 = new Boat(props1, 5);

        final BoatProperties props2 = new BoatProperties(WorldObjectType.BOAT, 1, "Small Boat", 1, 1, USAGE_DIFFICULTY, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF, MAX_HIT, MAX_HIT);
        boat2 = new Boat(props2, 1);

        object = boat1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        final Boat clone = (Boat) boat1.clone();

        // Make sure all fields match
        assertEquals(boat1.amount, clone.amount);
        assertEquals(boat1.properties, clone.properties);

        // Make sure the object itself is different
        assertNotSame(boat1, clone);


        final Boat clone2 = (Boat) boat2.clone();

        // Make sure all fields match
        assertEquals(boat2.amount, clone2.amount);
        assertEquals(boat2.properties, clone2.properties);

        // Make sure the object itself is different
        assertNotSame(boat2, clone2);
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        boat1.use(character);
        boat2.use(character);
    }

    @Test
    public void testGetMinHit() {
        assertEquals(MIN_HIT, boat1.getMinHit());
        assertEquals(MAX_HIT, boat2.getMinHit());
    }

    @Test
    public void testGetMaxHit() {
        assertEquals(MAX_HIT, boat1.getMaxHit());
        assertEquals(MAX_HIT, boat2.getMaxHit());
    }

    @Test
    public void testGetDamageBonus() {
        final int damage = boat1.getDamageBonus();

        assertThat(damage, greaterThanOrEqualTo(MIN_HIT));
        assertThat(damage, lessThanOrEqualTo(MAX_HIT));
        assertEquals(MAX_HIT, boat2.getDamageBonus());
    }

    @Test
    @Override
    public void testCanBeStolen() {
        assertFalse(boat1.canBeStolen());
        assertFalse(boat2.canBeStolen());
    }

    @Test
    public void testGetUsageDifficulty() {
        assertEquals(USAGE_DIFFICULTY, boat1.getUsageDifficulty());
        assertEquals(USAGE_DIFFICULTY, boat2.getUsageDifficulty());
    }
}
