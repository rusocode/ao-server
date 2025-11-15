package com.ao.service.map;

import com.ao.data.dao.CityDAO;
import com.ao.data.dao.MapDAO;
import com.ao.model.map.Tile;
import com.ao.model.map.Map;
import com.ao.service.AreaService;
import com.ao.service.MapService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapServiceImplTest {

    @Test
    public void testLoadMaps() {
        MapDAO dao = mock(MapDAO.class);
        CityDAO cityDao = mock(CityDAO.class);
        AreaService areaService = mock(AreaService.class);
        MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();
    }

    @Test
    public void testGetMap() {
        int mapId = 1;
        Map map = new Map(null, mapId, (short) 1, new Tile[]{});
        MapDAO dao = mock(MapDAO.class);
        CityDAO cityDao = mock(CityDAO.class);
        AreaService areaService = mock(AreaService.class);

        when(dao.load()).thenReturn(new Map[]{map});

        MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();
        assertThat(service.getMap(mapId)).isSameAs(map);
    }

}
