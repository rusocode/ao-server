package com.ao.model.character.npc.properties;

import com.ao.model.character.NpcType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.Heading;

/**
 * Define the properties of the npc.
 * <p>
 * Allows a lightweight pattern implementation.
 */

public class Npc {

    protected int id;
    protected String name;
    protected NpcType type;
    protected short body;
    protected short head;
    protected Heading heading;
    protected boolean respawn;
    protected String description; // TODO Deberia ser una lista? Hay tres npcs que tienen mas de una desc pero ni se usan :p
    protected Class<? extends Behavior> behavior;
    protected Class<? extends AttackStrategy> attackStrategy;
    protected Class<? extends MovementStrategy> movementStrategy;

    public Npc(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawn, String description,
               Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.body = body;
        this.head = head;
        this.heading = heading;
        this.respawn = respawn;
        this.description = description;
        this.behavior = behavior;
        this.attackStrategy = attackStrategy;
        this.movementStrategy = movementStrategy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getBody() {
        return body;
    }

    public NpcType getType() {
        return type;
    }

    public short getHead() {
        return head;
    }

    public Heading getHeading() {
        return heading;
    }

    public boolean canRespawn() {
        return respawn;
    }

    public Class<? extends Behavior> getBehavior() {
        return behavior;
    }

    public Class<? extends AttackStrategy> getAttackStrategy() {
        return attackStrategy;
    }

    public Class<? extends MovementStrategy> getmovementStrategy() {
        return movementStrategy;
    }

    public String getDescription() {
        return description;
    }

}