package com.ao.model.object;

import com.ao.model.character.Character;
import com.ao.model.object.properties.DefensiveItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class HelmetTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Helmet helmet1;
    private Helmet helmet2;

    @BeforeEach
    public void setUp() throws Exception {
        DefensiveItemProperties props1 = new DefensiveItemProperties(ObjectType.HELMET, 1, "Viking Helmet", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        helmet1 = new Helmet(props1, 5);

        DefensiveItemProperties props2 = new DefensiveItemProperties(ObjectType.HELMET, 1, "Viking Helmet", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        helmet2 = new Helmet(props2, 1);

        object = helmet1;
        ammount = 5;
        objectProps = props1;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        Helmet clone = (Helmet) helmet1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(helmet1.amount);
        assertThat(clone.properties).isEqualTo(helmet1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(helmet1);


        Helmet clone2 = (Helmet) helmet2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(helmet2.amount);
        assertThat(clone2.properties).isEqualTo(helmet2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(helmet2);
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);

        // nothing should happen
        helmet1.use(character);
        helmet2.use(character);

        verifyNoInteractions(character);
    }

}
