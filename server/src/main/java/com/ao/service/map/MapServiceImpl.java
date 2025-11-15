package com.ao.service.map;

import com.ao.action.ActionExecutor;
import com.ao.data.dao.CityDAO;
import com.ao.data.dao.MapDAO;
import com.ao.model.character.Character;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.Map;
import com.ao.service.AreaService;
import com.ao.service.MapService;
import com.google.inject.Inject;

/**
 * Concrete implementation of MapService.
 */

public class MapServiceImpl extends ActionExecutor<MapService> implements MapService {

    private final MapDAO mapDAO; // Sin DI tendria que hardcodear el objeto -> new WorldMapDAOImpl("maps/", 1, "maps.properties");
    private final CityDAO cityDAO;
    private final AreaService areaService;
    private Map[] maps;
    private City[] cities;

    @Inject
    public MapServiceImpl(MapDAO mapDAO, CityDAO cityDAO, AreaService areaService) {
        this.mapDAO = mapDAO;
        this.cityDAO = cityDAO;
        this.areaService = areaService;
    }

    @Override
    public void loadMaps() {
        maps = mapDAO.load();
    }

    @Override
    public Map getMap(int id) {
        if (id < 1 || id > maps.length) return null;
        // Maps enumeration starts at 1, not 0
        return maps[id - 1];
    }

    @Override
    public void loadCities() {
        cities = cityDAO.load();
    }

    @Override
    public City getCity(byte id) {
        if (id < 1 || id > cities.length) return null;
        // City enumeration starts at 1, not 0
        return cities[id - 1];
    }

    @Override
    public void putCharacterAtPos(Character chara, Position pos) {
        Map map = getMap(pos.getMap());
        map.putCharacterAtPos(chara, pos.getX(), pos.getY());
        areaService.addCharToMap(map, chara);
    }

    @Override
    public void moveCharacterTo(Character character, Heading heading) {
        Position position = character.getPosition();
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

        Map map = getMap(position.getMap());

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