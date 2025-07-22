package com.ao.model.character.npc.properties;

import com.ao.model.character.Alignment;
import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.map.Heading;

public class NobleNPCProperties extends NPCProperties {

    protected Alignment alignment;

    /**
     * Creates a new NobleNPCProperties instance.
     *
     * @param type             npc's type
     * @param id               npc's id
     * @param name             npc's name
     * @param body             npc's body
     * @param head             npc's head
     * @param heading          npc's heading
     * @param respawn          npc's respawn
     * @param description      npc's description
     * @param behavior         npc's behavior
     * @param attackStrategy   npc's attack strategy
     * @param movementStrategy npc's movement strategy
     * @param alignment        The NPC's alignment
     */
    public NobleNPCProperties(NPCType type, int id, String name, short body,
                              short head, Heading heading, boolean respawn, String description,
                              Class<? extends Behavior> behavior,
                              Class<? extends AttackStrategy> attackStrategy,
                              Class<? extends MovementStrategy> movementStrategy, Alignment alignment) {
        super(type, id, name, body, head, heading, respawn, description,
                behavior, attackStrategy, movementStrategy);
        this.alignment = alignment;
    }

    public Alignment getAlignment() {
        return alignment;
    }

}
