package com.ao.model.object;

import com.ao.model.object.properties.ResourceSourceProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractResourceSourceTest extends AbstractObjectTest {

    @Test
    public void testResourceWorldObjctId() {
        assertThat(((AbstractResourceSource) object).getResourceWorldObjctId()).isEqualTo(((ResourceSourceProperties) objectProps).getResourceWorldObjctId());
    }

}
