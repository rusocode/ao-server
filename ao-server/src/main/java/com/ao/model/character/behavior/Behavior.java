package com.ao.model.character.behavior;

import com.ao.model.character.Character;

/**
 * Defines a character's behavior (AI)
 */

public interface Behavior {

    /**
     * Tells the behavior that another attacked the character.
     *
     * @param character character that attacked
     */
    void attackedBy(Character character);

    /**
     * Performs an action according to the behavior
     */
    void takeAction();

}
