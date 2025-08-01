package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;
import org.junit.jupiter.api.BeforeEach;

public class TreeTest extends AbstractResourceSourceTest {

    private static final ResourceSourceType resourceSourceType = ResourceSourceType.TREE;

    @BeforeEach
    public void setUp() throws Exception {
        ResourceSourceProperties props1 = new ResourceSourceProperties(WorldObjectType.TREE, 1, "Elven Tree", 1, 5, resourceSourceType);
        object = new Tree(props1);
        objectProps = props1;
    }

}
