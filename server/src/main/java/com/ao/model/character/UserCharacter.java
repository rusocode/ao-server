package com.ao.model.character;

import com.ao.model.character.archetype.Archetype;

public interface UserCharacter extends Character {

    /**
     * Retrieves the user's requested skill points.
     *
     * @param skill skill
     * @return the amount of skill points
     */
    int getSkill(Skill skill);

    /**
     * Retrieves the character's strength.
     *
     * @return the character's current strength
     */
    byte getStrength();

    /**
     * Retrieves the character's dexterity.
     *
     * @return the character's current dexterity
     */
    byte getDexterity();

    /**
     * Retrieves the character's intelligence.
     *
     * @return the character's current intelligence
     */
    byte getIntelligence();

    /**
     * Retrieves the character's charisma.
     *
     * @return the character's current charisma
     */
    byte getCharisma();

    /**
     * Retrieves the character's constitution.
     *
     * @return the character's current constitution
     */
    byte getConstitution();

    /**
     * Adds points to the given user's skill.
     *
     * @param skill  skill to add points
     * @param points points to add
     */
    void addToSkill(Skill skill, byte points);

    /**
     * Checks if the user is doing some work.
     *
     * @return True if the user is working, false otherwise
     */
    boolean isWorking();

    /**
     * Sets the user to work.
     */
    void work();

    /**
     * Retrieves the user's guild name.
     *
     * @return the user's guild name
     */
    String getGuildName();

    /**
     * Sets the user's guild name.
     *
     * @param name guild name to be set
     */
    void setGuildName(String name);

    /**
     * Retrieves the user's party id.
     *
     * @return the user's party id if the user belongs to one, -1 otherwise
     */
    int getPartyId();

    /**
     * Sets the user's party id.
     *
     * @param id party id to be set
     */
    void setPartyId(int id);

    /**
     * Retrieves the user's race.
     *
     * @return the user's race
     */
    Race getRace();

    /**
     * Retrieves the user's gender.
     *
     * @return the user's gender
     */
    Gender getGender();

    /**
     * Retrieves the user's archetype.
     *
     * @return the user's archetype
     */
    Archetype getArchetype();

    /**
     * Retrieves the user's stamina.
     *
     * @return the user's stamina
     */
    public int getStamina();

    /**
     * Sets the user's stamina.
     *
     * @param stamina stamina
     */
    public void setStamina(int stamina);

    /**
     * Retrieves the user's max stamina.
     *
     * @return the user's max stamina
     */
    public int getMaxStamina();

    /**
     * Sets the user's max stamina.
     *
     * @param maxStamina max stamina
     */
    public void setMaxStamina(int maxStamina);

    /**
     * Checks if the user is meditating.
     *
     * @return True if the user is meditating, false otherwise
     */
    boolean isMeditating();

    /**
     * Sets the user to meditate.
     *
     * @param meditating boolean value
     */
    void setMeditate(boolean meditating);

}
