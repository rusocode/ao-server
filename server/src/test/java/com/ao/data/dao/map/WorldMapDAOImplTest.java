package com.ao.data.dao.map;

import com.ao.data.dao.WorldMapDAO;
import com.ao.model.map.Position;
import com.ao.model.map.Trigger;
import com.ao.model.map.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorldMapDAOImplTest {

    private static final String MAPS_PATH = "maps/";
    private static final String MAPS_CONFIG_FILE = "maps.properties";
    private WorldMapDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new WorldMapDAOImpl(MAPS_PATH, 1, MAPS_CONFIG_FILE);
    }

    @Test
    public void testLoadMaps() {
        WorldMap[] maps = dao.load();
        final WorldMap map = maps[0];

        // Check for blocked/non-blocked
        assertThat(map.getTile(0, 0).isBlocked()).isTrue();
        assertThat(map.getTile(83, 28).isBlocked()).isFalse();

        // Check if tile exits are where expected
        assertThat(map.getTile(49, 49).getTileExit()).isNull();

        final Position tileExit = map.getTile(19, 84).getTileExit();
        assertThat(tileExit != null).isTrue();
        assertThat(tileExit.getX()).isGreaterThanOrEqualTo((byte) WorldMap.MIN_X);
        assertThat(tileExit.getX()).isLessThanOrEqualTo((byte) WorldMap.MAX_X);
        assertThat(tileExit.getX()).isGreaterThanOrEqualTo((byte) WorldMap.MIN_Y);
        assertThat(tileExit.getX()).isLessThanOrEqualTo((byte) WorldMap.MAX_Y);

        assertThat(Trigger.NONE).isEqualTo(map.getTile(0, 0).getTrigger());
        assertThat(Trigger.UNDER_ROOF).isEqualTo(map.getTile(23, 32).getTrigger());
        assertThat(Trigger.INVALID_POSITION).isEqualTo(map.getTile(57, 44).getTrigger());
        assertThat(Trigger.SAFE_ZONE).isEqualTo(map.getTile(40, 74).getTrigger());
        assertThat(Trigger.ANTI_PICKET).isEqualTo(map.getTile(28, 20).getTrigger());
        assertThat(Trigger.FIGHT_ZONE).isEqualTo(map.getTile(23, 33).getTrigger());

        assertThat(map.getTile(71, 55).isLava()).isTrue();
        assertThat(map.getTile(71, 55).isWater()).isFalse();

        assertThat(map.getTile(76, 72).isLava()).isFalse();
        assertThat(map.getTile(76, 72).isWater()).isTrue();
    }

}
