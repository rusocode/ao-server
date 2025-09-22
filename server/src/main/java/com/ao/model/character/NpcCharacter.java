package com.ao.model.character;

public interface NpcCharacter extends Character {

    int getId();

    int getExperience();

    int getGold();

    Character getMaster();

    NpcType getNPCType();

    boolean isTameable();

    boolean canTrade();

    boolean isHostile();

    boolean hasMaster();

}