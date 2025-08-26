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
 * Defines a NPC's properties. Allows a lightweight pattern implementation.
 */

public class CreatureNPCProperties extends NPCProperties {

    protected int experience;
    protected int gold;
    protected int minHP; // TODO Esto es realmente necesario? Me parece una pelotudez cargar la vida inicial.
    protected int maxHP;
    protected int minDamage;
    protected int maxDamage;
    protected short defense;
    protected short magicDefense;
    protected short accuracy;
    protected short dodge;
    protected List<Spell> spells;
    protected boolean canSwim;
    protected boolean canWalk;
    protected boolean attackable;
    protected boolean poison;
    protected boolean paralyzable;
    protected boolean hostile;
    protected boolean tameable;
    protected Drop drop;

    /**
     * Creates a new CreatureNPCProperties instance.
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
     * @param movementStrategy the npc's movement strategy
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
     * @param canSwim          whether the npc is can swim or not
     * @param canWalk          whether the npc is can walk or not
     * @param attackable       whether the npc is attackable or not
     * @param poison           whether the npc is poison or not
     * @param paralyzable      whether the npc is paralyzable or not
     * @param hostile          whether the npc is hostile or not
     * @param tameable         whether the npc is tameable or not
     * @param drop             npcs drop
     */
    public CreatureNPCProperties(NPCType type, int id, String name, short body, short head,
                                 Heading heading, boolean respawn, String description, Class<? extends Behavior> behavior,
                                 Class<? extends AttackStrategy> attackStrategy, Class<? extends MovementStrategy> movementStrategy,
                                 int experience, int gold, int minHP, int maxHP,
                                 int minDamage, int maxDamage, short defense, short magicDefense, short accuracy,
                                 short dodge, List<Spell> spells, boolean canSwim, boolean canWalk, boolean attackable,
                                 boolean poison, boolean paralyzable, boolean hostile, boolean tameable, Drop drop) {
        super(type, id, name, body, head, heading, respawn, description, behavior, attackStrategy, movementStrategy);

        this.experience = experience;
        this.gold = gold;
        this.minHP = minHP;
        this.maxHP = maxHP;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.accuracy = accuracy;
        this.dodge = dodge;
        this.spells = spells;
        this.canSwim = canSwim;
        this.canWalk = canWalk;
        this.attackable = attackable;
        this.poison = poison;
        this.paralyzable = paralyzable;
        this.hostile = hostile;
        this.tameable = tameable;
        this.drop = drop;
    }

    public int getGold() {
        return gold;
    }

    public int getExperience() {
        return experience;
    }

    public int getMinHP() {
        return minHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public short getDefense() {
        return defense;
    }

    public short getMagicDefense() {
        return magicDefense;
    }

    public short getAccuracy() {
        return accuracy;
    }

    public short getDodge() {
        return dodge;
    }

    public boolean canSwim() {
        return canSwim;
    }

    public boolean canWalk() {
        return canWalk;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public boolean canPoison() {
        return poison;
    }

    public boolean isParalyzable() {
        return paralyzable;
    }

    public boolean isHostile() {
        return hostile;
    }

    public boolean isTameable() {
        return tameable;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public Drop getDrop() {
        return drop;
    }

}