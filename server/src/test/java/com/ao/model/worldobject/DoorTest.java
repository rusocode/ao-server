package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DoorProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoorTest extends AbstractWorldObjectTest {

    private static final boolean OPEN = true;
    private static final boolean LOCKED = false;
    private static final int CODE = 123;
    private static final DoorProperties OTHER_PROPERTIES = null;

    private Door door1;

    @BeforeEach
    public void setUp() throws Exception {
        final DoorProperties props1 = new DoorProperties(WorldObjectType.DOOR, 1, "Puerta abierta", 1, OPEN, LOCKED, CODE, OTHER_PROPERTIES);
        door1 = new Door(props1);
        object = door1;
        objectProps = props1;
    }

    @Test
    public void testGetOpen() {
        assertThat(door1.getOpen()).isTrue();
    }

    @Test
    public void testGetLocked() {
        assertThat(door1.getLocked()).isFalse();
    }

    @Test
    public void testGetCode() {
        assertThat(door1.getCode()).isEqualTo(CODE);
    }

}