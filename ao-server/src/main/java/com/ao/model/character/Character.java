package com.ao.model.character;

import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.area.AreaInfo;
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.*;

public interface Character {

    // FIXME I fon't like these being here...
    public static final int MAX_THIRSTINESS = 100;
    public static final int MAX_HUNGER = 100;

    /**
     * Retrieves the character's hit points.
     *
     * @return the character's hit points
     */
    int getHitPoints();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's hit points.
     *
     * @param points points to add
     */
    void addToHitPoints(int points);

    /**
     * Retrieves the maximum character's hit points.
     *
     * @return the maximum character's hit points
     */
    int getMaxHitPoints();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's max hit points.
     *
     * @param points points to add
     */
    void addToMaxHitPoints(int points);

    /**
     * Retrieves the character's mana points.
     *
     * @return the character's mana points
     */
    int getMana();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's mana points.
     *
     * @param points points to add
     */
    void addToMana(int points);

    /**
     * Retrieves the maximum character's mana points.
     *
     * @return the maximum character's mana points
     */
    int getMaxMana();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's maximum mana points.
     *
     * @param points points to add
     */
    void addToMaxMana(int points);

    /**
     * Retrieves the character's thirstiness.
     *
     * @return the character's thirstiness points
     */
    int getThirstiness();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's thirstiness.
     *
     * @param points points to add
     */
    void addToThirstiness(int points);

    /**
     * Retrieves the character's hunger points.
     *
     * @return the character's hunger points
     */
    int getHunger();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's hunger.
     *
     * @param points points
     */
    void addToHunger(int points);

    /**
     * Checks if the character is paralyzed.
     *
     * @return true if the character is paralyzed, false otherwise
     */
    boolean isParalyzed();

    /**
     * Sets whether the character is paralyzed or not.
     *
     * @param paralyzed character's paralysis status
     */
    void setParalyzed(boolean paralyzed);

    /**
     * Checks if the character is immobilized.
     *
     * @return true if the character is paralyzed, false otherwise
     */
    boolean isImmobilized();

    /**
     * Sets whether the character is immobilized or not.
     *
     * @param immobilized character's immobilization status
     */
    void setImmobilized(boolean immobilized);

    /**
     * Retrieves the character's position.
     *
     * @return the character's position
     */
    Position getPosition();

    /**
     * Retrieves the character's current area info.
     *
     * @return the character's area info
     */
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

    /**
     * Retrieves a character's inventory.
     *
     * @return the character's inventory
     */
    Inventory getInventory();

    /**
     * Retrieves the character's reputation.
     *
     * @return the character's reputation
     */
    Reputation getReputation();

    /**
     * Retrieves the user's nickname.
     *
     * @return the user's name
     */
    String getName();

    /**
     * Retrieves the user's display name (includes status, guild, etc).
     *
     * @return the user's display name
     */
    String getDisplayName();

    /**
     * Retrieves the Character's description.
     *
     * @return the Character's description
     */
    String getDescription();

    /**
     * Retrieves the character's body.
     *
     * @return the character's body
     */
    int getBody();

    /**
     * Sets the character's body
     *
     * @param body the new body
     */
    void setBody(int body);

    /**
     * Retrieves the character's head.
     *
     * @return the character's head
     */
    int getHead();

    /**
     * Sets the character's head
     *
     * @param head the new head
     */
    void setHead(int head);

    /**
     * Retrieves the original character's body.
     *
     * @return the character's body
     */
    int getOriginalBody();

    /**
     * Retrieves the original character's head.
     *
     * @return the character's head
     */
    int getOriginalHead();

    /**
     * Retrieves the character's poisoning status.
     *
     * @return the character's poisoning status
     */
    boolean isPoisoned();

    /**
     * Sets whether the character is poisoned or not.
     *
     * @param poisoned new character's poisoning status
     */
    void setPoisoned(boolean poisoned);

    /**
     * Retrieves the character's level.
     *
     * @return the character's level
     */
    byte getLevel();

    /**
     * Retrieves the character's experience.
     *
     * @return the character's experience
     */
    int getExperience();

    /**
     * Retrieves the experience needed to level up
     *
     * @return the experience needed to level up
     */
    int getExperienceForLeveUp();

    /**
     * Adds experience to the character's current experience. Will level up if possible.
     *
     * @param experience experience to add
     */
    void addToExperience(int experience);

    /**
     * Retrieves the character's status.
     *
     * @return true if the character is dead, false otherwise
     */
    boolean isDead();

    /**
     * Sets whether the character is dead or not.
     *
     * @param dead character's status
     */
    void setDead(boolean dead);

    /**
     * Checks if the user is mimetized, or not.
     *
     * @return true if the user is mimetized, false otherwise
     */
    boolean isMimetized();

    /**
     * Sets whether the character is mimetized or not.
     *
     * @param mimetized character's mimetized status
     */
    void setMimetized(boolean mimetized);

    /**
     * Checks if the user is invisible, or not.
     *
     * @return true if the user is invisible, false otherwise
     */
    boolean isInvisible();

    /**
     * Sets whether the user is invisible or not.
     *
     * @param invisible user's invisibility status
     */
    void setInvisible(boolean invisible);

    /**
     * Checks if the user is hidden, or not.
     *
     * @return true if the user is hidden, false otherwise
     */
    boolean isHidden();

    /**
     * Sets whether the user is hidden or not.
     *
     * @param hidden user's concealment status
     */
    void setHidden(boolean hidden);

    /**
     * Checks if the user is dumb, or not.
     *
     * @return true if the user is dumb, false otherwise
     */
    boolean isDumb();

    /**
     * Sets whether the user is dumb, or not.
     *
     * @param dumb new user's dumb status
     */
    void setDumb(boolean dumb);

    /**
     * Checks if the character can walk in the water.
     *
     * @return true if the character can move in water, false otherwise
     */
    boolean canWalkInWater();

    /**
     * Checks if the character can walk in the earth.
     *
     * @return true if the character can walk in the earth
     */
    boolean canWalkInEarth();

    /**
     * Performs an attack on the given character.
     *
     * @param character target to attack
     */
    void attack(Character character);

    /**
     * Moves the character on the given direction.
     *
     * @param heading heading in which to move
     */
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

    /**
     * Retrieves the character's spell list.
     *
     * @return the character's spells
     */
    Spell[] getSpells();

    /**
     * Adds (or subtracts if the given number is negative) points to the character's dexterity.
     *
     * @param points   points to add
     * @param duration time for which the effect is valid
     */
    void addToDexterity(int points, int duration);

    /**
     * Adds (or subtracts if the given number is negative) points to the character's strength.
     *
     * @param points   points to add
     * @param duration time for which the effect is valid
     */
    void addToStrength(int points, int duration);

    /**
     * Adds the given spell.
     *
     * @param spell spell to be added
     */
    void addSpell(Spell spell);

    /**
     * Retrieves the character's money.
     *
     * @return the character's money
     */
    int getMoney();

    /**
     * Increases the amount of money.
     *
     * @param amount of gold to add
     */
    void addMoney(int amount);

    /**
     * Retrieves the character's heading.
     *
     * @return the character's heading
     */
    Heading getHeading();

    /**
     * Sets the character heading.
     *
     * @param heading heading
     */
    void setHeading(Heading heading);

    /**
     * Retrieves the character's weapon.
     *
     * @return the character's weapon
     */
    Weapon getWeapon();

    /**
     * Sets the character weapon.
     *
     * @param weapon character's weapon
     */
    void setWeapon(Weapon weapon);

    /**
     * Retrieves the character's weapon
     *
     * @return the character's weapon
     */
    Helmet getHelmet();

    /**
     * Sets the character helmet.
     *
     * @param helmet character's helmet
     */
    void setHelmet(Helmet helmet);

    /**
     * Retrieves the character's shield.
     *
     * @return the character's shield
     */
    Shield getShield();

    /**
     * Sets the character shield.
     *
     * @param shield character's shield
     */
    void setShield(Shield shield);

    /**
     * Retrieves the character's armor
     *
     * @return the character's armor
     */
    Armor getArmor();

    /**
     * Sets the character's armor.
     *
     * @param armor character's armor
     */
    void setArmor(Armor armor);

    /**
     * Retrieves the character's accessory
     *
     * @return the character's accessory
     */
    Accessory getAccessory();

    /**
     * Sets the character's accessory.
     *
     * @param accessory character's accessory
     */
    void setAccessory(Accessory accessory);

    /**
     * Retrieves the character's privileges.
     *
     * @return the character's privileges
     */
    Privileges getPrivileges();

    /**
     * Sets the character privileges.
     *
     * @param privileges character's privileges
     */
    void setPrivileges(Privileges privileges);

    /**
     * Retrieves the character's nick's color.
     *
     * @return the character's nick's color
     */
    int getNickColor();

    /**
     * Sets the character nick's color.
     *
     * @param colorIndex character's nick's color
     */
    void setNickColor(int colorIndex);

    /**
     * Retrieves the character's fx.
     *
     * @return the character's fx
     */
    Fx getFx();

    /**
     * Sets the character's fx.
     *
     * @param fx character's fx
     */
    void setFx(Fx fx);

    /**
     * Retrieves the character's index.
     *
     * @return the character's index
     */
    short getCharIndex();

    /**
     * Sets the character's index.
     *
     * @param index character's index
     */
    void setCharIndex(int index);

    /**
     * Checks if the character hidden as Admin.
     *
     * @return true if is admin-hidden, false otherwise
     */
    boolean isAdminHidden();

    /**
     * Sets the character hidden as Admin.
     *
     * @param adminHidden boolean value
     */
    void setAdminHidden(boolean adminHidden);

    /**
     * Checks if the character is sailing.
     *
     * @return true if it is sailing, false otherwise.
     */
    boolean isSailing();

    /**
     * Sets the character sailing.
     *
     * @param sailing boolean value
     */
    void setSailing(boolean sailing);

    /**
     * Checks if the item is equipped.
     *
     * @return whether the item is equipped or not
     */
    boolean isEquipped(Item item);

}
