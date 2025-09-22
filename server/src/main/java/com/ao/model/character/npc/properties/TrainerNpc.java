package com.ao.model.character.npc.properties;

import com.ao.model.character.NpcType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.Heading;

import java.util.Map;

public class TrainerNpc extends Npc {

    protected Map<Integer, String> creatures;

    public TrainerNpc(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawn,
                      String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                      Class<? extends MovementStrategy> movementStrategy, Map<Integer, String> creatures) {
        super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);
        this.creatures = creatures;
    }

    public Map<Integer, String> getCreatures() {
        return creatures;
    }

}
