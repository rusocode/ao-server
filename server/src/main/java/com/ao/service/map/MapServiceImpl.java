package com.ao.service.map;

import com.ao.action.ActionExecutor;
import com.ao.data.dao.CityDAO;
import com.ao.data.dao.WorldMapDAO;
import com.ao.model.character.Character;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.WorldMap;
import com.ao.service.MapService;

import com.google.inject.Inject;

/**
 * Concrete implementation of MapService.
 */

public class MapServiceImpl extends ActionExecutor<MapService> implements MapService {

    private final WorldMapDAO mapsDAO;
    private final CityDAO citiesDAO;
    private final AreaService areaService;
    private WorldMap[] maps;
    private City[] cities;

    @Inject
    public MapServiceImpl(final WorldMapDAO mapsDAO, final CityDAO citiesDAO, final AreaService areaService) {
        this.mapsDAO = mapsDAO;
        this.citiesDAO = citiesDAO;
        this.areaService = areaService;
    }

    @Override
    public void loadMaps() {
        maps = mapsDAO.retrieveAll();
    }

    @Override
    public WorldMap getMap(final int id) {
        if (id < 1 || id > maps.length) return null;
        // Maps enumeration starts at 1, not 0
        return maps[id - 1];
    }

    @Override
    public void loadCities() {
        cities = citiesDAO.loadAll();
    }

    @Override
    public City getCity(final byte id) {
        if (id < 1 || id > cities.length) return null;
        // City enumeration starts at 1, not 0
        return cities[id - 1];
    }

    @Override
    public void putCharacterAtPos(final Character chara, final Position pos) {
        final WorldMap map = getMap(pos.getMap());
        map.putCharacterAtPos(chara, pos.getX(), pos.getY());
        areaService.addCharToMap(map, chara);
    }

    @Override
    public void moveCharacterTo(final Character character, final Heading heading) {
        final Position position = character.getPosition();
        byte x = position.getX();
        byte y = position.getY();
        switch (heading) {
            case NORTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y--;
                break;
            case WEST:
                x--;
                break;
        }

        final WorldMap map = getMap(position.getMap());

        // Check if the position is available
        if (map.isTileAvailable(x, y, !character.isSailing(), character.isSailing(), true, true)) {
            // TODO In newer version of the game, you can push ghosts and invisible admins
            // Update the map
            map.moveCharacterTo(character, x, y);
            // Update the character
            position.setX(x);
            position.setY(y);
            // TODO Check tile events
            // TODO Check & notify area change
        }
    }

    @Override
    protected MapService getService() {
        return this;
    }

}