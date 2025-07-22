package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractResourceSourceTest extends AbstractWorldObjectTest {

    @Test
    public void testResourceWorldObjctId() {
        assertEquals(((ResourceSourceProperties) objectProps).getResourceWorldObjctId(), ((AbstractResourceSource) object).getResourceWorldObjctId());
    }

}
