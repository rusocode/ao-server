package com.ao.service.map;

import com.ao.data.dao.CityDAO;
import com.ao.data.dao.MapDAO;
import com.ao.model.map.Map;
import com.ao.model.map.Tile;
import com.ao.service.AreaService;
import com.ao.service.MapService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapServiceImplTest {

    @Test
    public void testLoadMaps_mapsAccessibleAfterLoad() {
        Map map1 = new Map(null, 1, (short) 1, new Tile[]{}, 0, false, false, "BOSQUE", "CAMPO", false, 0, false);
        Map map2 = new Map(null, 2, (short) 1, new Tile[]{}, 0, false, false, "CIUDAD", "CAMPO", false, 0, false);
        MapDAO dao = mock(MapDAO.class);
        CityDAO cityDao = mock(CityDAO.class);
        AreaService areaService = mock(AreaService.class);
        when(dao.load()).thenReturn(new Map[]{map1, map2});

        MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();

        assertThat(service.getMap(1)).isSameAs(map1);
        assertThat(service.getMap(2)).isSameAs(map2);
    }

    @Test
    public void testLoadMaps_outOfRangeIdReturnsNull() {
        MapDAO dao = mock(MapDAO.class);
        CityDAO cityDao = mock(CityDAO.class);
        AreaService areaService = mock(AreaService.class);
        when(dao.load()).thenReturn(new Map[]{});

        MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();

        assertThat(service.getMap(0)).isNull();
        assertThat(service.getMap(99)).isNull();
    }

    @Test
    public void testGetMap() {
        int mapId = 1;
        Map map = new Map(null, mapId, (short) 1, new Tile[]{}, 0, false, false, "BOSQUE", "CAMPO", false, 0, false);
        MapDAO dao = mock(MapDAO.class);
        CityDAO cityDao = mock(CityDAO.class);
        AreaService areaService = mock(AreaService.class);

        when(dao.load()).thenReturn(new Map[]{map});

        MapService service = new MapServiceImpl(dao, cityDao, areaService);
        service.loadMaps();
        assertThat(service.getMap(mapId)).isSameAs(map);
    }

}
