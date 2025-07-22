package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;
import org.junit.Before;

public class MineTest extends AbstractResourceSourceTest {

    private static final ResourceSourceType resourceSourceType = ResourceSourceType.MINE;

    private Mine mine1;

    @Before
    public void setUp() throws Exception {
        final ResourceSourceProperties props1 = new ResourceSourceProperties(WorldObjectType.MINE, 1, "Cooper mine", 1, 6, resourceSourceType);
        mine1 = new Mine(props1);

        object = mine1;
        objectProps = props1;
    }
}
