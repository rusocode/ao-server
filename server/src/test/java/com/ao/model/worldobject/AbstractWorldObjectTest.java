package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.WorldObjectProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractWorldObjectTest {

    protected AbstractWorldObject object;
    protected WorldObjectProperties objectProps;

    @Test
    public void testGetId() {
        assertThat(object.getId()).isEqualTo(objectProps.getId());
    }

    @Test
    public void testGetGraphic() {
        assertThat(object.getGraphic()).isEqualTo(objectProps.getGraphic());
    }

    @Test
    public void testGetName() {
        assertThat(object.getName()).isEqualTo(objectProps.getName());
    }

}
