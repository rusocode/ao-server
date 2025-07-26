package com.ao.model.character.npc.properties;

import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.character.npc.Drop;
import com.ao.model.map.Heading;
import com.ao.model.spell.Spell;

import java.util.List;

/**
 * Defines a Guard NPC's properties. Allows a lightweight pattern implementation.
 */

public class GuardNPCProperties extends CreatureNPCProperties {

    protected boolean originalPosition;

    /**
     * Creates a new GuardNPCProperties instance.
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
     * @param movementStrategy The npc's movement strategy
     * @param experience       npc's experience
     * @param gold             npc's gold
     * @param minHP            npc's min hp
     * @param maxHP            npc's max hp
     * @param minDamage        npc's min damage
     * @param maxDamage        npc's max damage
     * @param defense          npc's defense
     * @param magicDefense     npc's magic defense
     * @param accuracy         npc's accuracy
     * @param dodge            npc's dodge
     * @param spells           npc's spells
     * @param canSwim          whether the npc can swim or not
     * @param canWalk          whether the npc can walk or not
     * @param attackable       whether the npc is attackable or not
     * @param poison           whether the npc is poison or not
     * @param paralyzable      whether the npc is paralyzable or not
     * @param hostile          whether the npc is hostile or not
     * @param tameable         whether the npc is tameable or not
     * @param drop             NPCs drop
     * @param originalPosition whether the npc is an original position or not
     */
    public GuardNPCProperties(NPCType type, int id, String name, short body,
                              short head, Heading heading, boolean respawn, String description,
                              Class<? extends Behavior> behavior,
                              Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy,
                              int experience, int gold, int minHP, int maxHP, int minDamage,
                              int maxDamage, short defense, short magicDefense, short accuracy,
                              short dodge, List<Spell> spells, boolean canSwim, boolean canWalk,
                              boolean attackable, boolean poison, boolean paralyzable,
                              boolean hostile, boolean tameable, Drop drop, boolean originalPosition) {
        super(type, id, name, body, head, heading, respawn, description,
                behavior, attackStrategy, movementStrategy, experience, gold, minHP, maxHP, minDamage,
                maxDamage, defense, magicDefense, accuracy, dodge, spells,
                canSwim, canWalk, attackable, poison, paralyzable, hostile,
                tameable, drop);
        this.originalPosition = originalPosition;
    }

    public boolean hasOriginalPosition() {
        return originalPosition;
    }

}