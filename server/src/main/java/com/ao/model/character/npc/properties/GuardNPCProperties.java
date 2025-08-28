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

    public GuardNPCProperties(NPCType type, int id, String name, short body, short head, Heading heading, boolean respawn, String description,
                              Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy,
                              int experience, int gold, int minHP, int maxHP, int minDamage, int maxDamage, short defense, short magicDefense, short accuracy,
                              short dodge, List<Spell> spells, boolean canSwim, boolean canWalk, boolean attackable, boolean poison, boolean paralyzable,
                              boolean hostile, boolean tameable, Drop drop, boolean originalPosition) {
        super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy, experience, gold, minHP, maxHP, minDamage,
                maxDamage, defense, magicDefense, accuracy, dodge, spells, canSwim, canWalk, attackable, poison, paralyzable, hostile, tameable, drop);
        this.originalPosition = originalPosition;
    }

    public boolean hasOriginalPosition() {
        return originalPosition;
    }

}