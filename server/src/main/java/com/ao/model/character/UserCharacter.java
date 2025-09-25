package com.ao.model.character;

import com.ao.model.character.archetype.Archetype;

/**
 * <b>Model/entity interface</b> where it is defined <b>WHAT</b> a user character <b>IS</b>.
 */

public interface UserCharacter extends Character {

    int getSkill(Skill skill);

    byte getStrength();

    byte getDexterity();

    byte getIntelligence();

    byte getCharisma();

    byte getConstitution();

    void addToSkill(Skill skill, byte points);

    boolean isWorking();

    void work();

    String getGuildName();

    void setGuildName(String name);

    int getPartyId();

    void setPartyId(int id);

    Race getRace();

    Gender getGender();

    Archetype getArchetype();

    public int getStamina();

    public void setStamina(int stamina);

    public int getMaxStamina();

    public void setMaxStamina(int maxStamina);

    boolean isMeditating();

    void setMeditate(boolean meditating);

}
