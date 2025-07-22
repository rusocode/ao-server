package com.ao.model.character.attack;

import com.ao.model.character.Character;

public interface AttackStrategy {

    /**
     * Performs an attack on the given character.
     *
     * @param character target to attack
     */
    void attack(Character character);

}
