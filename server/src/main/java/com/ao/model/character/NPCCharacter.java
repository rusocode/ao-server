package com.ao.model.character;

public interface NPCCharacter extends Character {

    /**
     * Retrieves the NPC's id.
     *
     * @return the NPC's id
     */
    int getId();

    /**
     * Retrieves the NPC's experience.
     *
     * @return the NPC's experience
     */
    int getExperience();

    /**
     * Retrieves the NPC's gold.
     *
     * @return the NPC's gold
     */
    int getGold();

    /**
     * Retrieves the NPC's master if it has one.
     *
     * @return the NPC's master if it has one
     */
    Character getMaster();

    /**
     * Retrieves the NPC's Type.
     *
     * @return the NPC's Type
     */
    NPCType getNPCType();

    /**
     * Checks if the NPC is tameable or not.
     *
     * @return true if the NPC is tameable, false otherwise
     */
    boolean isTameable();

    /**
     * Checks if the NPC can trade or not.
     *
     * @return true if the NPC can trade, false otherwise
     */
    boolean canTrade();

    /**
     * Checks if the NPC is hostile or not.
     *
     * @return true if the NPC is hostile, false otherwise
     */
    boolean isHostile();

    /**
     * Checks if the NPC has a master.
     *
     * @return true if it has a master, false otherwise
     */
    boolean hasMaster();

}