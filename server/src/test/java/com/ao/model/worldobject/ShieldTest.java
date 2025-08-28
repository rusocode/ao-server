package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class ShieldTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Shield shield1;
    private Shield shield2;

    @BeforeEach
    public void setUp() throws Exception {
        DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.SHIELD, 1, "Turtle Shield", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        shield1 = new Shield(props1, 5);

        DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.SHIELD, 1, "Turtle Shield", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        shield2 = new Shield(props2, 1);

        object = shield1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        Shield clone = (Shield) shield1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(shield1.amount);
        assertThat(clone.properties).isEqualTo(shield1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(shield1);

        Shield clone2 = (Shield) shield2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(shield2.amount);
        assertThat(clone2.properties).isEqualTo(shield2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(shield2);
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);

        // nothing should happen
        shield1.use(character);
        shield2.use(character);

        verifyNoInteractions(character);
    }

}
