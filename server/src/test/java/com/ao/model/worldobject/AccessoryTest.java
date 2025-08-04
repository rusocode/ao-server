package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.DefensiveItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AccessoryTest extends AbstractDefensiveItemTest {

    private static final int MIN_DEF = 1;
    private static final int MAX_DEF = 5;

    private static final int MIN_MAGIC_DEF = 10;
    private static final int MAX_MAGIC_DEF = 50;

    private Accessory accessory1;
    private Accessory accessory2;

    @BeforeEach
    public void setUp() throws Exception {
        final DefensiveItemProperties props1 = new DefensiveItemProperties(WorldObjectType.ACCESSORY, 1, "Gold Ring", 1, 1, 0, null, null, false, false, false, false, 1, MIN_DEF, MAX_DEF, MIN_MAGIC_DEF, MAX_MAGIC_DEF);
        accessory1 = new Accessory(props1, 5);

        final DefensiveItemProperties props2 = new DefensiveItemProperties(WorldObjectType.ACCESSORY, 1, "Diamond Ring", 1, 1, 0, null, null, false, false, false, false, 1, MAX_DEF, MAX_DEF, MAX_MAGIC_DEF, MAX_MAGIC_DEF);
        accessory2 = new Accessory(props2, 1);

        object = accessory1;
        objectProps = props1;
        ammount = 5;
        itemEquipped = false;
    }

    @Test
    public void testClone() {
        final Accessory clone = (Accessory) accessory1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(accessory1.amount);
        assertThat(clone.properties).isEqualTo(accessory1.properties);

        // Make sure the object itself is different 
        assertThat(clone).isNotSameAs(accessory1);

        final Accessory clone2 = (Accessory) accessory2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(accessory2.amount);
        assertThat(clone2.properties).isEqualTo(accessory2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(accessory2);
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        accessory1.use(character);
        accessory2.use(character);
    }

}
