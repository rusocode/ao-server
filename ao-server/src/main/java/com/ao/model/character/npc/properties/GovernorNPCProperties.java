package com.ao.model.character.npc.properties;

import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.City;
import com.ao.model.map.Heading;

/**
 * Defines a NPC's properties. Allows a lightweight pattern implementation.
 */
public class GovernorNPCProperties extends NPCProperties {

	protected City city;

	/**
	 * Creates a new GovernorNPCProperties instance.
	 * @param type the npc's type.
	 * @param id the npc's id.
	 * @param name the npc's name.
	 * @param body the npc's body.
	 * @param head the npc's head.
	 * @param heading the npc's heading.
	 * @param respawn the npc's respawn.
	 * @param description the npc's description
	 * @param behavior the npc's behavior.
	 * @param attackStrategy the npc's attack strategy.
	 * @param movementStrategy
	 * @param city The city
	 */
	public GovernorNPCProperties(NPCType type, int id, String name, short body, short head,
			Heading heading, boolean respawn, String description, Class<? extends Behavior> behavior,
			Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy, City city) {
		super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);

		this.city = city;
	}

	/**
	 * @return the npc's city.
	 */
	public City getCity() {
		return city;
	}

}