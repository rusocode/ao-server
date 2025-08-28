package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.KeyProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyTest extends AbstractWorldObjectTest {

    private static final int CODE = 123;

    private Key key1;

    @BeforeEach
    public void setUp() throws Exception {
        KeyProperties props1 = new KeyProperties(WorldObjectType.KEY, 1, "Llave maestra", 1, 1, 0, null, null, false, false, false, false, CODE);
        key1 = new Key(props1, 1);

        object = key1;
        objectProps = props1;
    }

    @Test
    public void testClone() {
        Key clone = (Key) key1.clone();

        // Make sure all fields match
        assertThat(clone.amount).isEqualTo(key1.amount);
        assertThat(clone.properties).isEqualTo(key1.properties);

        // Make sure the object itself is different
        assertThat(clone).isNotSameAs(key1);

    }

    @Test
    public void testGetMinHit() {
        assertThat(key1.getCode()).isEqualTo(CODE);
    }

}
