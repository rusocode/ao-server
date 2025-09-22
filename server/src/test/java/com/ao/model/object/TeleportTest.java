package com.ao.model.object;

import com.ao.model.object.properties.TeleportProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeleportTest extends AbstractObjectTest {

    private static final int RADIUS = 4;

    private Teleport teleport1;

    @BeforeEach
    public void setUp() throws Exception {
        TeleportProperties props1 = new TeleportProperties(ObjectType.TELEPORT, 1, "Teleport", 1, RADIUS);
        teleport1 = new Teleport(props1);

        object = teleport1;
        objectProps = props1;
    }

    @Test
    public void testGetRadius() {
        assertThat(teleport1.getRadius()).isEqualTo(RADIUS);
    }

}
