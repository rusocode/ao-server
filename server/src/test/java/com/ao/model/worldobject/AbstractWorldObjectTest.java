package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.WorldObjectProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractWorldObjectTest {

    protected AbstractWorldObject object;
    protected WorldObjectProperties objectProps;

    @Test
    public void testGetId() {
        assertEquals(objectProps.getId(), object.getId());
    }

    @Test
    public void testGetGraphic() {
        assertEquals(objectProps.getGraphic(), object.getGraphic());
    }

    @Test
    public void testGetName() {
        assertEquals(objectProps.getName(), object.getName());
    }

}
