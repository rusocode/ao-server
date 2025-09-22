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
import com.ao.model.worldobject.Object;

public class LoggedUser extends ConnectedUser implements UserCharacter {

    private final Reputation reputation;
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
    public LoggedUser(ConnectedUser user, Reputation reputation, Race race, Gender gender, Archetype archetype, boolean poisoned, boolean paralyzed,
                      boolean immobilized, boolean invisible, boolean mimetized, boolean dumbed, boolean hidden, int maxMana,
                      int minMana, int maxHp, int minHp, int maxThirstiness, int minThirstiness, int maxHunger, int minHunger, byte lvl,
                      String name, String description) {
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
        return null;
    }

    @Override
    public void setGuildName(String name) {
    }

    @Override
    public int getPartyId() {
        return 0;
    }

    @Override
    public void setPartyId(int id) {
    }

    @Override
    public Race getRace() {
        return race;
    }

    @Override
    public int getSkill(Skill skill) {
        return 0;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    @Override
    public void work() {
    }

    @Override
    public void addToExperience(int experience) {

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
    }

    @Override
    public boolean canWalkInEarth() {
        return false;
    }

    @Override
    public boolean canWalkInWater() {
        return false;
    }

    @Override
    public void cast(Spell spell, Character target) {
    }

    @Override
    public void cast(Spell spell, Object object) {
    }

    @Override
    public void equip(EquipableItem item) {
    }

    @Override
    public int getAttackPower() {
        return 0;
    }

    @Override
    public int getBody() {
        return 0;
    }

    @Override
    public void setBody(int body) {
    }

    @Override
    public int getDefensePower() {
        return 0;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    public int getExperienceForLeveUp() {
        return 0;
    }

    @Override
    public int getHead() {
        return 0;
    }

    @Override
    public void setHead(int head) {
    }

    @Override
    public int getHitPoints() {
        return minHp;
    }

    @Override
    public int getHunger() {
        return minHunger;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public byte getLevel() {
        return level;
    }

    @Override
    public int getMana() {
        return minMana;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHp;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public String getName() {
        // TODO If we shouldn't show the name (.showName is False), return an empty string

        StringBuilder builder = new StringBuilder(name);

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
        return 0;
    }

    @Override
    public int getOriginalHead() {
        return 0;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Reputation getReputation() {
        return reputation;
    }

    @Override
    public Spell[] getSpells() {
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
    }

    @Override
    public boolean isDumb() {
        return dumbed;
    }

    @Override
    public void setDumb(boolean dumb) {
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
    }

    @Override
    public void addToDexterity(int points, int duration) {
    }

    @Override
    public void addToStrength(int points, int duration) {
    }

    @Override
    public void addSpell(Spell spell) {
    }

    @Override
    public void addMoney(int amount) {
    }

    @Override
    public int getMoney() {
        return 0;
    }

    @Override
    public Heading getHeading() {
        return Heading.EAST;
    }

    @Override
    public void setHeading(Heading heading) {
    }

    @Override
    public Privileges getPrivileges() {
        return new Privileges(1);
    }

    @Override
    public void setPrivileges(Privileges privileges) {
    }

    @Override
    public int getNickColor() {
        return 0;
    }

    @Override
    public void setNickColor(int colorIndex) {
    }

    @Override
    public Fx getFx() {
        return null;
    }

    @Override
    public void setFx(Fx fx) {
    }

    @Override
    public short getCharIndex() {
        return 0;
    }

    @Override
    public void setCharIndex(int index) {
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
    }

    @Override
    public Helmet getHelmet() {
        return helmet;
    }

    @Override
    public void setHelmet(Helmet helmet) {
    }

    @Override
    public Shield getShield() {
        return shield;
    }

    @Override
    public void setShield(Shield shield) {
    }

    @Override
    public Armor getArmor() {
        return armor;
    }

    @Override
    public void setArmor(Armor armor) {
    }

    @Override
    public Accessory getAccessory() {
        return accessory;
    }

    @Override
    public void setAccessory(Accessory accessory) {
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
    }

    @Override
    public boolean isEquipped(Item item) {
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
