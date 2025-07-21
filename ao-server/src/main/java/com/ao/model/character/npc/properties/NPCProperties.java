package com.ao.model.character.npc.properties;

import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.Heading;

/**
 * Defines a NPC's properties. Allows a lightweight pattern implementation.
 */
public class NPCProperties {

	protected int id;
	protected String name;
	protected NPCType type;
	protected short body;
	protected short head;
	protected Heading heading;
	protected boolean respawn;
	protected String description; // TODO : Debería ser una lista? Hay tres NPCs que tienen más de una desc pero ni se usan :p
	protected Class<? extends Behavior> behavior;
	protected Class<? extends AttackStrategy> attackStrategy;
	protected Class<? extends MovementStrategy> movementStrategy;

	/**
	 * Creates a new NPCProperties instance.
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
	 * @param movementStrategy the npc's movement strategy.
	 */
	public NPCProperties(NPCType type, int id, String name, short body, short head,
		Heading heading, boolean respawn, String description, Class<? extends Behavior> behavior,
		Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy) {
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

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the graphic
	 */
	public short getBody() {
		return body;
	}

	/**
	 * @return the type
	 */
	public NPCType getType() {
		return type;
	}

	/**
	 * @return the graphic
	 */
	public short getHead() {
		return head;
	}

	/**
	 * @return the graphic
	 */
	public Heading getHeading() {
		return heading;
	}

	/**
	 * @return respawn
	 */
	public boolean canRespawn() {
		return respawn;
	}

	/**
	 * @return the behavior
	 */
	public Class<? extends Behavior> getBehavior() {
		return behavior;
	}

	/**
	 * @return the attackStrategy
	 */
	public Class<? extends AttackStrategy> getAttackStrategy() {
		return attackStrategy;
	}

	/**
	 * @return the movementStrategy
	 */
	public Class<? extends MovementStrategy> getmovementStrategy() {
		return movementStrategy;
	}

	/**
	 * @return the npc's description.
	 */
	public String getDescription() {
		return description;
	}
}