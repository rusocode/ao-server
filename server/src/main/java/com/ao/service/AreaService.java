package com.ao.service;

import com.ao.model.character.Character;
import com.ao.model.map.Heading;
import com.ao.model.map.Map;
import com.ao.model.user.LoggedUser;

public interface AreaService {

    void checkIfUserNeedsUpdate(Map map, Character character, Heading heading);

    void addCharToMap(Map map, Character character);

    void removeUserFromMap(Map map, LoggedUser user);

}