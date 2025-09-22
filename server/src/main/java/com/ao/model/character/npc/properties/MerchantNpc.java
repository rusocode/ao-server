package com.ao.model.character.npc.properties;

import com.ao.model.character.NpcType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.worldobject.ObjectType;

import java.util.Set;

public class MerchantNpc extends Npc {

    protected Inventory inventory;
    protected boolean respawnInventory;
    protected Set<ObjectType> acceptedTypes;

    public MerchantNpc(NpcType type, int id, String name, short body, short head,
                       Heading heading, boolean respawn, Class<? extends Behavior> behavior,
                       Class<? extends AttackStrategy> attackStrategy,
                       Class<? extends MovementStrategy> movementStrategy, String description,
                       Inventory inventory, boolean respawnInventory, Set<ObjectType> acceptedTypes) {
        super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);
        this.inventory = inventory;
        this.respawnInventory = respawnInventory;
        this.acceptedTypes = acceptedTypes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean canRespawnInventory() {
        return respawnInventory;
    }

    public Set<ObjectType> getAcceptedTypes() {
        return acceptedTypes;
    }

}