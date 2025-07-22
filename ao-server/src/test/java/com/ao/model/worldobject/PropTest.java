package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.TeleportProperties;
import org.junit.Before;

public class PropTest extends AbstractWorldObjectTest {

    private static final int RADIUS = 4;

    private Prop prop1;

    @Before
    public void setUp() throws Exception {
        final TeleportProperties props1 = new TeleportProperties(WorldObjectType.PROP, 1, "Teleport", 1, RADIUS);
        prop1 = new Prop(props1);

        object = prop1;
        objectProps = props1;
    }

}
