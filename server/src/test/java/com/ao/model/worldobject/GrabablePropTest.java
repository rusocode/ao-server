package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.ItemProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class GrabablePropTest extends AbstractItemTest {

    private GrabableProp prop1;
    private GrabableProp prop2;

    @BeforeEach
    public void setUp() throws Exception {
        final ItemProperties props1 = new ItemProperties(WorldObjectType.GRABABLE_PROP, 1, "Black Potion", 1, 1, null, null, false, true, false, true);
        prop1 = new GrabableProp(props1, 5);

        final ItemProperties props2 = new ItemProperties(WorldObjectType.GRABABLE_PROP, 1, "Black Potion", 1, 1, null, null, false, false, false, true);
        prop2 = new GrabableProp(props2, 1);

        object = prop2;
        objectProps = props2;
        ammount = 1;
    }

    @Test
    public void testUse() {
        final Character character = mock(Character.class);

        // nothing should happen
        prop1.use(character);
        prop2.use(character);

        verifyNoInteractions(character);
    }

    @Test
    public void testClone() {
        final GrabableProp clone = (GrabableProp) prop1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(prop1.amount);
        assertThat(clone.properties).isEqualTo(prop1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(prop1);

        final GrabableProp clone2 = (GrabableProp) prop2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(prop2.amount);
        assertThat(clone2.properties).isEqualTo(prop2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(prop2);
    }

}
