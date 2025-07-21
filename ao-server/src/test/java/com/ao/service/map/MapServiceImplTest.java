

package com.ao.service.map;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.ao.data.dao.CityDAO;
import com.ao.data.dao.WorldMapDAO;
import com.ao.model.map.Tile;
import com.ao.model.map.WorldMap;
import com.ao.service.MapService;

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
		final WorldMap map = new WorldMap(null, mapId, (short) 1, new Tile[] {});
		final WorldMapDAO dao = mock(WorldMapDAO.class);
		final CityDAO cityDao = mock(CityDAO.class);
		final AreaService areaService = mock(AreaService.class);

		when(dao.retrieveAll()).thenReturn(new WorldMap[] {map});

		final MapService service = new MapServiceImpl(dao, cityDao, areaService);
		service.loadMaps();

		assertSame(map, service.getMap(mapId));
	}

}
