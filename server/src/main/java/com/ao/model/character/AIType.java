package com.ao.model.character;

import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.behavior.NullBehavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.character.movement.QuietMovementStrategy;

/**
 * Enum with a legacy AITypes, which are now useless.
 */

public enum AIType {

    // TODO Complete this as we code the behaviors!
    STATIC(1, NullBehavior.class, null, QuietMovementStrategy.class),
    RANDOM(2, null, null, null),
    BAD_ATTACKS_GOOD(3, null, null, null),
    DEFENSIVE(4, null, null, null),
    GUARD_ATTACK_CRIMINALS(5, null, null, null),
    NPC_OBJECT(6, null, null, QuietMovementStrategy.class),
    FOLLW_MASTER(8, null, null, null),
    ATTACK_NPC(9, null, null, null),
    PATHFINDING(10, null, null, null),
    PRETORIAN_PRIEST(20, null, null, null),
    PRETORIAN_WARRIOR(21, null, null, null),
    PRETORIAN_MAGE(22, null, null, null),
    PRETORIAN_HUNTER(23, null, null, null),
    PRETORIAN_KING(24, null, null, null);

    private final int id;
    private final Class<? extends Behavior> behavior;
    private final Class<? extends AttackStrategy> attackStrategy;
    private final Class<? extends MovementStrategy> movementStrategy;

    AIType(int id, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy) {
        this.id = id;
        this.behavior = behavior;
        this.attackStrategy = attackStrategy;
        this.movementStrategy = movementStrategy;
    }

    // @Nullable TODO Tendria que ir esta anotacion?
    public static AIType findById(int id) {
        for (AIType aiType : values())
            if (aiType.id == id) return aiType;
        return null;
    }

    public Class<? extends Behavior> getBehavior() {
        return behavior;
    }

    public Class<? extends AttackStrategy> getAttackStrategy() {
        return attackStrategy;
    }

    public Class<? extends MovementStrategy> getMovementStrategy() {
        return movementStrategy;
    }

    public boolean isPretorian() {
        return this == PRETORIAN_HUNTER || this == PRETORIAN_KING || this == PRETORIAN_MAGE || this == PRETORIAN_PRIEST || this == PRETORIAN_WARRIOR;
    }

}
