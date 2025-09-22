package com.ao.model.character.npc.properties;

import com.ao.model.character.NpcType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.City;
import com.ao.model.map.Heading;

public class GovernorNpc extends Npc {

    protected City city;

    public GovernorNpc(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawn,
                       String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                       Class<? extends MovementStrategy> movementStrategy, City city) {
        super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);
        this.city = city;
    }

    public City getCity() {
        return city;
    }

}