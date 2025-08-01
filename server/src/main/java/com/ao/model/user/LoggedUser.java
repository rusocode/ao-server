package com.ao.model.user;

import com.ao.model.character.*;
import com.ao.model.character.Character;
import com.ao.model.character.archetype.Archetype;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.area.AreaInfo;
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.*;

/**
 * Defines a logged user.
 */

public class LoggedUser extends ConnectedUser implements UserCharacter {
    private final Reputation reputation;
    // Constructor
    private final Race race;
    private final Gender gender;
    private final Archetype archetype;
    private AreaInfo areaInfo;
    private Inventory inventory; // TODO need to be instanced in the
    private Weapon weapon;
    private Helmet helmet;
    private Shield shield;
    private Armor armor;
    private Accessory accessory;

    /*
     * UserFlags
     */
    private boolean poisoned;
    private boolean paralyzed;
    private boolean immobilized;
    private boolean invisible;
    private boolean mimetized;
    private boolean dumbed;
    private boolean hidden;
    private boolean meditating;
    private boolean sailing;

    /*
     * AdminFlags
     */
    private boolean adminHidden;

    /*
     * UserStats
     */
    private int maxMana;
    private int maxHp;
    private int minMana;
    private int minHp;
    private int minThirstiness;
    private int maxThirstiness;
    private int maxHunger;
    private int minHunger;
    private int maxStamina;
    private int stamina;
    private byte level;
    private String name;
    private String description;

    // TODO Prohibit building this class without a builder (Effective Java, item 2)
    public LoggedUser(final ConnectedUser user, final Reputation reputation,
                      final Race race, final Gender gender, final Archetype archetype,
                      final boolean poisoned, final boolean paralyzed,
                      final boolean immobilized, final boolean invisible,
                      final boolean mimetized, final boolean dumbed, final boolean hidden,
                      final int maxMana, final int minMana, final int maxHp, final int minHp,
                      final int maxThirstiness, final int minThirstiness,
                      final int maxHunger, final int minHunger, final byte lvl,
                      final String name, final String description) {
        super(user.getConnection());
        this.reputation = reputation;
        this.race = race;
        this.gender = gender;
        this.archetype = archetype;
        this.poisoned = poisoned;
        this.paralyzed = paralyzed;
        this.immobilized = immobilized;
        this.invisible = invisible;
        this.mimetized = mimetized;
        this.dumbed = dumbed;
        this.hidden = hidden;
        this.maxMana = maxMana;
        this.maxHp = maxHp;
        this.maxMana = maxMana;
        this.minHp = minHp;
        this.maxThirstiness = maxThirstiness;
        this.maxHunger = maxHunger;
        this.minThirstiness = minThirstiness;
        this.minHunger = minHunger;
        level = lvl;
        this.name = name;
        this.description = description;
    }

    @Override
    public void addToSkill(Skill skill, byte points) {
        // TODO Auto-generated method stub
    }

    @Override
    public Archetype getArchetype() {
        return archetype;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public String getGuildName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setGuildName(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getPartyId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setPartyId(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Race getRace() {
        return race;
    }

    @Override
    public int getSkill(Skill skill) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isWorking() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void work() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addToExperience(int experience) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addToHitPoints(int points) {
        minHp += points; // TODO Check for overflows and underflows
        if (minHp > maxHp) minHp = maxHp;
    }

    @Override
    public void addToHunger(int points) {
        minHunger += points; // TODO Check for overflows and underflows
        if (minHunger > maxHunger) minHunger = maxHunger;
    }

    @Override
    public void addToMana(int points) {
        minMana += points; // TODO Check for overflows and underflows
        if (minMana > maxMana) minMana = maxMana;
    }

    @Override
    public void addToMaxHitPoints(int points) {
        maxHp += points; // TODO Check for overflows and underflows
    }

    @Override
    public void addToMaxMana(int points) {
        maxMana += points; // TODO Check for overflows and underflows
    }

    @Override
    public void addToThirstiness(int points) {
        minThirstiness += points; // TODO Check for overflows and underflows
        if (minThirstiness > maxThirstiness) minThirstiness = maxThirstiness;
    }

    @Override
    public void attack(Character character) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean canWalkInEarth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canWalkInWater() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void cast(Spell spell, Character target) {
        // TODO Auto-generated method stub
    }

    @Override
    public void cast(Spell spell, WorldObject object) {
        // TODO Auto-generated method stub
    }

    @Override
    public void equip(EquipableItem item) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getAttackPower() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getBody() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setBody(int body) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getDefensePower() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return description;
    }

    @Override
    public String getDisplayName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getExperience() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getExperienceForLeveUp() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHead() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setHead(int head) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getHitPoints() {
        // TODO Auto-generated method stub
        return minHp;
    }

    @Override
    public int getHunger() {
        // TODO Auto-generated method stub
        return minHunger;
    }

    @Override
    public Inventory getInventory() {
        // TODO Auto-generated method stub
        return inventory;
    }

    @Override
    public byte getLevel() {
        // TODO Auto-generated method stub
        return level;
    }

    @Override
    public int getMana() {
        // TODO Auto-generated method stub
        return minMana;
    }

    @Override
    public int getMaxHitPoints() {
        // TODO Auto-generated method stub
        return maxHp;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public String getName() {
        // TODO If we shouldn't show the name (.showName is False), return an empty string

        final StringBuilder builder = new StringBuilder(name);

        // TODO Translate this code from VB
//        If .flags.EnConsulta Then
//	        UserName = UserName & " " & TAG_CONSULT_MODE
//	    Else
//	        If UserList(sndIndex).flags.Privilegios And (PlayerType.User Or PlayerType.Consejero Or PlayerType.RoleMaster) Then
//	            If LenB(ClanTag) <> 0 Then _
//	                UserName = UserName & " <" & ClanTag & ">"
//	        Else
//	            If (.flags.invisible Or .flags.Oculto) And (Not .flags.AdminInvisible = 1) Then
//	                UserName = UserName & " " & TAG_USER_INVISIBLE
//	            Else
//	                If LenB(ClanTag) <> 0 Then _
//	                    UserName = UserName & " <" & ClanTag & ">"
//	            End If
//	        End If
//	    End If

        return builder.toString();
    }

    @Override
    public int getOriginalBody() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOriginalHead() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Reputation getReputation() {
        return reputation;
    }

    @Override
    public Spell[] getSpells() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getThirstiness() {
        return minThirstiness;
    }

    @Override
    public boolean isDead() {
        return minHp > 0 ? false : true;
    }

    @Override
    public void setDead(boolean dead) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isDumb() {
        return dumbed;
    }

    @Override
    public void setDumb(boolean dumb) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        // TODO If there is a timed event to remove hidden, remove it
        this.hidden = hidden;
    }

    @Override
    public boolean isImmobilized() {
        return immobilized;
    }

    @Override
    public void setImmobilized(boolean immobilized) {
        this.immobilized = immobilized;
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    @Override
    public boolean isMimetized() {
        return mimetized;
    }

    @Override
    public void setMimetized(boolean mimetized) {
        this.mimetized = mimetized;
    }

    @Override
    public boolean isParalyzed() {
        return paralyzed;
    }

    @Override
    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
    }

    @Override
    public boolean isPoisoned() {
        return poisoned;
    }

    @Override
    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    @Override
    public void use(Item item) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToDexterity(int points, int duration) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToStrength(int points, int duration) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addSpell(Spell spell) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addMoney(int amount) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getMoney() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Heading getHeading() {
        // TODO Auto-generated method stub
        return Heading.EAST;
    }

    @Override
    public void setHeading(Heading heading) {

    }

    @Override
    public Privileges getPrivileges() {
        // TODO Auto-generated method stub
        return new Privileges(1);
    }

    @Override
    public void setPrivileges(Privileges privileges) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getNickColor() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setNickColor(int colorIndex) {
        // TODO Auto-generated method stub
    }

    @Override
    public Fx getFx() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFx(Fx fx) {
        // TODO Auto-generated method stub
    }

    @Override
    public short getCharIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setCharIndex(int index) {
        // TODO Auto-generated method stub
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        // TODO Auto-generated method stub
    }

    @Override
    public Helmet getHelmet() {
        return helmet;
    }

    @Override
    public void setHelmet(Helmet helmet) {
        // TODO Auto-generated method stub
    }

    @Override
    public Shield getShield() {
        return shield;
    }

    @Override
    public void setShield(Shield shield) {
        // TODO Auto-generated method stub
    }

    @Override
    public Armor getArmor() {
        return armor;
    }

    @Override
    public void setArmor(Armor armor) {
        // TODO Auto-generated method stub
    }

    @Override
    public Accessory getAccessory() {
        return accessory;
    }

    @Override
    public void setAccessory(Accessory accessory) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    @Override
    public int getMaxStamina() {
        return maxStamina;
    }

    @Override
    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    @Override
    public boolean isMeditating() {
        return meditating;
    }

    @Override
    public void setMeditate(boolean meditating) {
        this.meditating = meditating;
    }

    @Override
    public boolean isAdminHidden() {
        return adminHidden;
    }

    @Override
    public void setAdminHidden(boolean adminHidden) {
        this.adminHidden = adminHidden;
    }

    @Override
    public boolean isSailing() {
        return sailing;
    }

    @Override
    public void setSailing(boolean sailing) {
        this.sailing = sailing;
    }

    @Override
    public void moveTo(Heading heading) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isEquipped(Item item) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AreaInfo getCurrentAreaInfo() {
        return areaInfo;
    }

    @Override
    public byte getStrength() {
        return getAttribute(Attribute.STRENGTH);
    }

    @Override
    public byte getDexterity() {
        return getAttribute(Attribute.DEXTERITY);
    }

    @Override
    public byte getIntelligence() {
        return getAttribute(Attribute.INTELLIGENCE);
    }

    @Override
    public byte getCharisma() {
        return getAttribute(Attribute.CHARISMA);
    }

    @Override
    public byte getConstitution() {
        return getAttribute(Attribute.CONSTITUTION);
    }

}
