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
    protected boolean attackable;
    protected boolean poison;
    protected boolean paralyzable;
    protected boolean hostile;
    protected boolean tameable;
    protected Drop drop;

    public CreatureNPCProperties(NPCType type, int id, String name, short body, short head, Heading heading, boolean respawn,
                                 String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                                 Class<? extends MovementStrategy> movementStrategy, int experience, int gold, int minHP, int maxHP,
                                 int minDamage, int maxDamage, short defense, short magicDefense, short accuracy,
                                 short dodge, List<Spell> spells, boolean canSwim, boolean attackable,
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