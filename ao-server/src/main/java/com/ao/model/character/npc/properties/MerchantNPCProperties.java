package com.ao.model.character.npc.properties;

import java.util.Set;

import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.worldobject.WorldObjectType;

/**
 * Defines a NPC's properties. Allows a lightweight pattern implementation.
 */
public class MerchantNPCProperties extends NPCProperties {

	protected Inventory inventory;
	protected boolean respawnInventory;
	protected Set<WorldObjectType> acceptedTypes;

	/**
	 * Creates a new GuardNPCProperties instance.
	 * @param type the npc's type.
	 * @param id the npc's id.
	 * @param name the npc's name.
	 * @param body the npc's body.
	 * @param head the npc's head.
	 * @param heading the npc's heading.
	 * @param respawn the npc's respawn.
	 * @param description the npc's description.
	 * @param behavior the npc's behavior.
	 * @param attackStrategy the npc's attack strategy.
	 * @param movementStrategy
	 * @param inventory the npc's inventory.
	 * @param respawnInventory Whether the npc's inventory has respawn or not.
	 * @param acceptedTypes the npc's items type.
	 */
	public MerchantNPCProperties(NPCType type, int id, String name, short body, short head,
			Heading heading, boolean respawn, Class<? extends Behavior> behavior,
			Class<? extends AttackStrategy> attackStrategy,
			Class<? extends MovementStrategy> movementStrategy, String description,
			Inventory inventory, boolean respawnInventory, Set<WorldObjectType> acceptedTypes) {
		super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);

		this.inventory = inventory;
		this.respawnInventory = respawnInventory;
		this.acceptedTypes  = acceptedTypes;
	}

	/**
	 * @return the npc's inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return True if the NPC can respawn inventory, false otherwise.
	 */
	public boolean canRespawnInventory() {
		return respawnInventory;
	}

	/**
	 * @return The WorldObjectType
	 */
	public Set<WorldObjectType> getAcceptedTypes() {
		return acceptedTypes;
	}
}