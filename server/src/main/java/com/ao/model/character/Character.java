package com.ao.model.character;

import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.area.AreaInfo;
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.*;

public interface Character {

    // FIXME I fon't like these being here...
    int MAX_THIRSTINESS = 100;
    int MAX_HUNGER = 100;

    int getHitPoints();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's hit points.
     *
     * @param points points to add
     */
    void addToHitPoints(int points);

    int getMaxHitPoints();

    void addToMaxHitPoints(int points);

    int getMana();

    void addToMana(int points);

    int getMaxMana();

    void addToMaxMana(int points);

    int getThirstiness();

    void addToThirstiness(int points);

    int getHunger();

    void addToHunger(int points);

    boolean isParalyzed();

    void setParalyzed(boolean paralyzed);

    boolean isImmobilized();

    void setImmobilized(boolean immobilized);

    Position getPosition();

    AreaInfo getCurrentAreaInfo();

    /**
     * Uses the given item (must be in the character's inventory).
     *
     * @param item item to use
     */
    void use(Item item);

    /**
     * Equips the given item (must be in the character's inventory).
     *
     * @param item item to use
     */
    void equip(EquipableItem item);

    /**
     * Retrieves the character's total attack power (considering items and effects).
     *
     * @return the character's total attack power (considering items and effects)
     */
    int getAttackPower();

    /**
     * Retrieves the character's total defense power (considering items and effects).
     *
     * @return the character's total defense power (considering items and effects)
     */
    int getDefensePower();

    Inventory getInventory();

    Reputation getReputation();

    String getName();

    /**
     * Retrieves the user's display name (includes status, guild, etc).
     *
     * @return the user's display name
     */
    String getDisplayName();

    String getDescription();

    int getBody();

    void setBody(int body);

    int getHead();

    void setHead(int head);

    int getOriginalBody();

    int getOriginalHead();

    boolean isPoisoned();

    void setPoisoned(boolean poisoned);

    byte getLevel();

    int getExperience();

    int getExperienceForLeveUp();

    /**
     * Adds experience to the character's current experience. Will level up if possible.
     *
     * @param experience experience to add
     */
    void addToExperience(int experience);

    boolean isDead();

    void setDead(boolean dead);

    boolean isMimetized();

    void setMimetized(boolean mimetized);

    boolean isInvisible();

    void setInvisible(boolean invisible);

    boolean isHidden();

    void setHidden(boolean hidden);

    boolean isDumb();

    void setDumb(boolean dumb);

    boolean canWalkInWater();

    boolean canWalkInEarth();

    /**
     * Performs an attack on the given character.
     *
     * @param character target to attack
     */
    void attack(Character character);

    void moveTo(Heading heading);

    /**
     * Casts a spell on the given character.
     *
     * @param spell  spell to cast.
     * @param target character on which to cast the spell
     */
    void cast(Spell spell, Character target);

    /**
     * Casts a spell on the given world object.
     *
     * @param spell  spell to cast.
     * @param object object on which to cast the spell
     */
    void cast(Spell spell, WorldObject object);

    Spell[] getSpells();

    void addToDexterity(int points, int duration);

    void addToStrength(int points, int duration);

    void addSpell(Spell spell);

    int getMoney();

    void addMoney(int amount);

    Heading getHeading();

    void setHeading(Heading heading);

    Weapon getWeapon();

    void setWeapon(Weapon weapon);

    Helmet getHelmet();

    void setHelmet(Helmet helmet);

    Shield getShield();

    void setShield(Shield shield);

    Armor getArmor();

    void setArmor(Armor armor);

    Accessory getAccessory();

    void setAccessory(Accessory accessory);

    Privileges getPrivileges();

    void setPrivileges(Privileges privileges);

    int getNickColor();

    void setNickColor(int colorIndex);

    Fx getFx();

    void setFx(Fx fx);

    short getCharIndex();

    void setCharIndex(int index);

    boolean isAdminHidden();

    void setAdminHidden(boolean adminHidden);

    boolean isSailing();

    void setSailing(boolean sailing);

    boolean isEquipped(Item item);

}
