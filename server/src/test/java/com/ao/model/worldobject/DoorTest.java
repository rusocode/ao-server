package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.DoorProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoorTest extends AbstractWorldObjectTest {

    private static final boolean OPEN = true;
    private static final boolean LOCKED = false;
    private static final int CODE = 123;
    private static final DoorProperties OTHER_PROPERTIES = null;

    private Door door1;

    @Before
    public void setUp() throws Exception {
        final DoorProperties props1 = new DoorProperties(WorldObjectType.DOOR, 1, "Puerta abierta", 1, OPEN, LOCKED, CODE, OTHER_PROPERTIES);
        door1 = new Door(props1);
        object = door1;
        objectProps = props1;
    }

    @Test
    public void testGetOpen() {
        assertTrue(door1.getOpen());
    }

    @Test
    public void testGetLocked() {
        assertFalse(door1.getLocked());
    }

    @Test
    public void testGetCode() {
        assertEquals(CODE, door1.getCode());
    }

}