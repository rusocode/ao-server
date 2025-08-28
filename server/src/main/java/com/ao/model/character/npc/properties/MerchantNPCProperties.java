package com.ao.model.character.npc.properties;

import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.worldobject.WorldObjectType;

import java.util.Set;

/**
 * Defines a NPC's properties. Allows a lightweight pattern implementation.
 */

public class MerchantNPCProperties extends NPCProperties {

    protected Inventory inventory;
    protected boolean respawnInventory;
    protected Set<WorldObjectType> acceptedTypes;

    public MerchantNPCProperties(NPCType type, int id, String name, short body, short head,
                                 Heading heading, boolean respawn, Class<? extends Behavior> behavior,
                                 Class<? extends AttackStrategy> attackStrategy,
                                 Class<? extends MovementStrategy> movementStrategy, String description,
                                 Inventory inventory, boolean respawnInventory, Set<WorldObjectType> acceptedTypes) {
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

    public Set<WorldObjectType> getAcceptedTypes() {
        return acceptedTypes;
    }

}