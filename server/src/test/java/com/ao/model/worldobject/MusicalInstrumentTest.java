package com.ao.model.worldobject;

import com.ao.model.character.Character;
import com.ao.model.worldobject.properties.MusicalInstrumentProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class MusicalInstrumentTest extends AbstractItemTest {

    private static final int ATTEMPTS = 100;

    private MusicalInstrument instrument1;
    private MusicalInstrument instrument2;

    private List<Integer> sounds1;
    private List<Integer> sounds2;

    @BeforeEach
    public void setUp() throws Exception {
        sounds1 = new LinkedList<>();
        sounds1.add(1);
        sounds1.add(3);
        sounds1.add(5);

        sounds2 = new LinkedList<>();
        sounds2.add(2);

         MusicalInstrumentProperties props1 = new MusicalInstrumentProperties(ObjectType.MUSICAL_INSTRUMENT, 1, "Horn", 1, 1, null, null, false, false, false, false, 1, sounds1);
        instrument1 = new MusicalInstrument(props1, 5);

         MusicalInstrumentProperties props2 = new MusicalInstrumentProperties(ObjectType.MUSICAL_INSTRUMENT, 1, "Drum", 1, 1, null, null, false, false, false, false, 1, sounds2);
        instrument2 = new MusicalInstrument(props2, 1);

        object = instrument1;
        objectProps = props1;
    }

    @Test
    public void testClone() {
         MusicalInstrument clone = (MusicalInstrument) instrument1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(instrument1.amount);
        assertThat(clone.properties).isEqualTo(instrument1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(instrument1);

         MusicalInstrument clone2 = (MusicalInstrument) instrument2.clone();

        // Make sure all fields match
        assertThat(clone2.amount).isEqualTo(instrument2.amount);
        assertThat(clone2.properties).isEqualTo(instrument2.properties);

        // Make sure the object itself is different
        assertThat(clone2).isNotSameAs(instrument2);
    }

    @Test
    public void testUse() {
         Character character = mock(Character.class);

        instrument1.use(character);
        instrument2.use(character);

        // Nothing should happen
        verifyNoInteractions(character);
    }

    @Test
    public void testGetSounds() {
        for (int i = 0; i < ATTEMPTS; i++)
            assertThat(instrument1.getSoundToPlay()).isIn(sounds1);
        assertThat(instrument2.getSoundToPlay()).isEqualTo(sounds2.get(0));
    }

}
