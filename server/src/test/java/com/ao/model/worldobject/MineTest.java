package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;
import org.junit.jupiter.api.BeforeEach;

public class MineTest extends AbstractResourceSourceTest {

    private static final ResourceSourceType resourceSourceType = ResourceSourceType.MINE;

    @BeforeEach
    public void setUp() throws Exception {
        ResourceSourceProperties props1 = new ResourceSourceProperties(ObjectType.MINE, 1, "Cooper mine", 1, 6, resourceSourceType);
        object = new Mine(props1);
        objectProps = props1;
    }

}
