package com.ao.service;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;

public interface CharacterBodyService {

    /**
     * Validate the character head.
     *
     * @param head   character's head
     * @param race   character's race
     * @param gender character's gender
     * @return true if is valid
     */
    boolean isValidHead(int head, Race race, Gender gender);

    /**
     * Returns the correct body for the given race and gender.
     *
     * @param race   character's race
     * @param gender character's gender
     * @return the character body
     */
    int getBody(Race race, Gender gender);

}