package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.BoatProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

    @BeforeEach
    public void setUp() throws Exception {
        BoatProperties props1 = new BoatProperties(ObjectType.BOAT, 1, "Small Boat", 1, 1, USAGE_DIFFICULTY, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF, MIN_HIT, MAX_HIT);
        boat1 = new Boat(props1, 5);

        BoatProperties props2 = new BoatProperties(ObjectType.BOAT, 1, "Small Boat", 1, 1, USAGE_DIFFICULTY, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF, MAX_HIT, MAX_HIT);
        boat2 = new Boat(props2, 1);

        object = boat1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        Boat clone = (Boat) boat1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(boat1.amount);
        assertThat(clone.properties).isEqualTo(boat1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(boat1);

        Boat clone2 = (Boat) boat2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(boat2.amount);
        assertThat(clone2.properties).isEqualTo(boat2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(boat2);
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);

        // nothing should happen
        boat1.use(character);
        boat2.use(character);
    }

    @Test
    public void testGetMinHit() {
        assertThat(boat1.getMinHit()).isEqualTo(MIN_HIT);
        assertThat(boat2.getMinHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetMaxHit() {
        assertThat(boat1.getMaxHit()).isEqualTo(MAX_HIT);
        assertThat(boat2.getMaxHit()).isEqualTo(MAX_HIT);
    }

    @Test
    public void testGetDamageBonus() {
        int damage = boat1.getDamageBonus();
        assertThat(damage).isBetween(MIN_HIT, MAX_HIT);
        assertThat(boat2.getDamageBonus()).isEqualTo(MAX_HIT);
    }

    @Test
    @Override
    public void testCanBeStolen() {
        assertThat(boat1.canBeStolen()).isFalse();
        assertThat(boat2.canBeStolen()).isFalse();
    }

    @Test
    public void testGetUsageDifficulty() {
        assertThat(boat1.getUsageDifficulty()).isEqualTo(USAGE_DIFFICULTY);
        assertThat(boat2.getUsageDifficulty()).isEqualTo(USAGE_DIFFICULTY);
    }

}
