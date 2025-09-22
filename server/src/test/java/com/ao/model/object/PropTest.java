package com.ao.model.object;

import com.ao.model.object.properties.TeleportProperties;
import org.junit.jupiter.api.BeforeEach;

public class PropTest extends AbstractObjectTest {

    private static final int RADIUS = 4;

    @BeforeEach
    public void setUp() throws Exception {
        TeleportProperties props1 = new TeleportProperties(ObjectType.PROP, 1, "Teleport", 1, RADIUS);
        object = new Prop(props1);
        objectProps = props1;
    }

}
