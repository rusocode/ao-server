package com.ao.model.character.behavior;

import com.ao.model.character.Character;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.movement.MovementStrategy;

/**
 * Behavior that just does nothing.
 */

public class NullBehavior implements Behavior {

    /**
     * Create a new null behavior.
     *
     * @param movement  movement behavior to be used
     * @param attack    attack strategy to be used
     * @param character character on which the behavior is applied
     */
    public NullBehavior(MovementStrategy movement, AttackStrategy attack, Character character) {
    }

    @Override
    public void attackedBy(Character character) {
        // We don't care
    }

    @Override
    public void takeAction() {
        // Do nothing
    }

}
