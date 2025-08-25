package com.ao.service.map;

import com.ao.data.dao.CityDAO;
import com.ao.data.dao.WorldMapDAO;
import com.ao.model.map.Tile;
import com.ao.model.map.WorldMap;
import com.ao.service.MapService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapServiceImplTest {

    @Test
    public void testLoadMaps() {
        final WorldMapDAO dao = mock(WorldMapDAO.class);
        final CityDAO cityDao = mock(CityDAO.class);
        final AreaService areaService = mock(AreaService.class);
        final MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();
    }

    @Test
    public void testGetMap() {
        final int mapId = 1;
        final WorldMap map = new WorldMap(null, mapId, (short) 1, new Tile[]{});
        final WorldMapDAO dao = mock(WorldMapDAO.class);
        final CityDAO cityDao = mock(CityDAO.class);
        final AreaService areaService = mock(AreaService.class);

        when(dao.load()).thenReturn(new WorldMap[]{map});

        final MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();
        assertThat(service.getMap(mapId)).isSameAs(map);
    }

}
