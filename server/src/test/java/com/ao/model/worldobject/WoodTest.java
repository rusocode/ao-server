package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.WoodProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class WoodTest extends AbstractItemTest {

    private Wood wood1, wood2;

    @BeforeEach
    public void setUp() throws Exception {
        WoodProperties props1 = new WoodProperties(WorldObjectType.WOOD, 1, "Black Potion", 1, 1, null, null, false, false, false, false, WoodType.NORMAL);
        wood1 = new Wood(props1, 5);

        WoodProperties props2 = new WoodProperties(WorldObjectType.WOOD, 1, "Black Potion", 1, 1, null, null, false, false, false, false, WoodType.ELVEN);
        wood2 = new Wood(props2, 1);

        object = wood2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUse() {
        Character character = mock(Character.class);

        // Nothing should happen
        wood1.use(character);
        wood2.use(character);

        verifyNoInteractions(character);
    }

    @Test
    public void testClone() {
        Wood clone = (Wood) wood1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(wood1.amount);
        assertThat(clone.properties).isEqualTo(wood1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(wood1);

        Wood clone2 = (Wood) wood2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(wood2.amount);
        assertThat(clone2.properties).isEqualTo(wood2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(wood2);
    }

    @Test
    public void testGetWoodType() {
        assertThat(wood1.getWoodType()).isEqualTo(WoodType.NORMAL);
        assertThat(wood2.getWoodType()).isEqualTo(WoodType.ELVEN);
    }

}
