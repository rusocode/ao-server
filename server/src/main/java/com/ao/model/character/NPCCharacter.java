package com.ao.model.character;

public interface NPCCharacter extends Character {

    int getId();

    int getExperience();

    int getGold();

    Character getMaster();

    NPCType getNPCType();

    boolean isTameable();

    boolean canTrade();

    boolean isHostile();

    boolean hasMaster();

}