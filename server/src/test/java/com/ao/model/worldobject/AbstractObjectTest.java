package com.ao.model.worldobject;

import com.ao.model.worldobject.properties.ObjectProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractObjectTest {

    protected AbstractObject object;
    protected ObjectProperties objectProps;

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
