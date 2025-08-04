package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ResourceSourceProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractResourceSourceTest extends AbstractWorldObjectTest {

    @Test
    public void testResourceWorldObjctId() {
        assertThat(((AbstractResourceSource) object).getResourceWorldObjctId()).isEqualTo(((ResourceSourceProperties) objectProps).getResourceWorldObjctId());
    }

}
