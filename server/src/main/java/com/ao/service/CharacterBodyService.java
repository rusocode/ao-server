package com.ao.service;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;

public interface CharacterBodyService {

    boolean isValidHead(int head, Race race, Gender gender);

    int getBody(Race race, Gender gender);

}