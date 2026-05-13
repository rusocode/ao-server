package com.ao.model.character.npc;

import com.ao.model.character.*;
import com.ao.model.character.Character;
import com.ao.model.character.npc.properties.Npc;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.Heading;
import com.ao.model.map.Position;
import com.ao.model.map.area.AreaInfo;
import com.ao.model.object.*;
import com.ao.model.object.Object;
import com.ao.model.spell.Spell;

/**
 * Implementacion concreta de NpcCharacter.
 * <p>
 * Equivalente a la instancia de NPC en VB6 (Npclist(NpcIndex)), pero en Java separamos las propiedades estaticas (Npc) del estado
 * dinamico (NpcCharacterImpl).
 */

public class NpcCharacterImpl implements NpcCharacter {

    // ===== Propiedades del NPC =====
    private final int npcId; // ID del tipo de NPC (equivalente a .Numero en VB6)
    private final Npc npc; // Propiedades estaticas desde npcs.dat
    // Privileges (todos los NPCs tienen privilegios de usuario normal)
    private final Privileges privileges = new Privileges((byte) 0);
    // ===== Estado del Character =====
    private short charIndex; // CharIndex asignado
    private String name;
    private String description;
    private int body;
    private int head;
    private int originalBody;
    private int originalHead;
    private Position position;
    private Heading heading;
    private AreaInfo areaInfo;
    // ===== Stats =====
    private int minHP;
    private int maxHP;
    private int minMana;
    private int maxMana;
    private int minThirstiness;
    private int minHunger;
    private byte level;
    private int gold;
    private int experience;
    // ===== Flags =====
    private boolean poisoned;
    private boolean paralyzed;
    private boolean immobilized;
    private boolean invisible;
    private boolean mimetized;
    private boolean hidden;
    private boolean dead;
    private boolean adminHidden;
    private boolean sailing;
    private boolean dumb;
    // ===== Equipment =====
    private Weapon weapon;
    private Helmet helmet;
    private Shield shield;
    private Armor armor;
    private Accessory accessory;
    private Fx fx;
    // ===== NPC-specific =====
    private Character master; // Amo del NPC si esta domado
    private Inventory inventory;
    private int nickColor; // Color del nombre (0 = blanco por defecto)

    public NpcCharacterImpl(Npc npc, Position position, short charIndex) {
        this.npcId = npc.getId();
        this.npc = npc;
        this.position = position;
        this.charIndex = charIndex;

    }

    @Override
    public int getId() {
        return this.npcId;
    }

    @Override
    public int getMinHitPoints() {
        return this.minHP;
    }

    @Override
    public void addToMinHitPoints(int points) {
        this.minHP += points;
        if (this.minHP > this.maxHP) this.minHP = this.maxHP;
    }

    @Override
    public int getMaxHitPoints() {
        return this.maxHP;
    }

    @Override
    public void addToMaxHitPoints(int points) {
        this.maxHP += points;
    }

    @Override
    public int getMinMana() {
        return this.minMana;
    }

    @Override
    public void addToMinMana(int points) {
        this.minMana += points;
        if (this.minMana > this.maxMana) this.minMana = this.maxMana;
    }

    @Override
    public int getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void addToMaxMana(int points) {
        this.maxMana += points;
    }

    @Override
    public int getMinStamina() {
        return 0;
    }

    @Override
    public void addToMinStamina(int points) {
    }

    @Override
    public int getMaxStamina() {
        return 0;
    }

    @Override
    public void addToMaxStamina(int points) {
    }

    @Override
    public int getMinThirstiness() {
        return this.minThirstiness;
    }

    @Override
    public void addToMinThirstiness(int points) {
        this.minThirstiness += points;
    }

    @Override
    public int getMinHunger() {
        return this.minHunger;
    }

    @Override
    public void addToMinHunger(int points) {
        this.minHunger += points;
    }

    @Override
    public boolean isParalyzed() {
        return false;
    }

    @Override
    public void setParalyzed(boolean paralyzed) {

    }

    @Override
    public boolean isImmobilized() {
        return false;
    }

    @Override
    public void setImmobilized(boolean immobilized) {

    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public AreaInfo getCurrentAreaInfo() {
        return null;
    }

    @Override
    public void use(Item item) {

    }

    @Override
    public void equip(EquipableItem item) {

    }

    @Override
    public int getAttackPower() {
        return 0;
    }

    @Override
    public int getDefensePower() {
        return 0;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Reputation getReputation() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getBody() {
        return 0;
    }

    @Override
    public void setBody(int body) {

    }

    @Override
    public int getHead() {
        return 0;
    }

    @Override
    public void setHead(int head) {

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
    public boolean isPoisoned() {
        return false;
    }

    @Override
    public void setPoisoned(boolean poisoned) {

    }

    @Override
    public byte getLevel() {
        return 0;
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
    public void addToExperience(int experience) {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void setDead(boolean dead) {

    }

    @Override
    public boolean isMimetized() {
        return false;
    }

    @Override
    public void setMimetized(boolean mimetized) {

    }

    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public void setInvisible(boolean invisible) {

    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public void setHidden(boolean hidden) {

    }

    @Override
    public boolean isDumb() {
        return false;
    }

    @Override
    public void setDumb(boolean dumb) {

    }

    @Override
    public boolean canWalkInWater() {
        return false;
    }

    @Override
    public boolean canWalkInEarth() {
        return false;
    }

    @Override
    public void attack(Character character) {

    }

    @Override
    public void moveTo(Heading heading) {

    }

    @Override
    public void cast(Spell spell, Character target) {

    }

    @Override
    public void cast(Spell spell, Object object) {

    }

    @Override
    public Spell[] getSpells() {
        return new Spell[0];
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
    public int getMoney() {
        return 0;
    }

    @Override
    public void addMoney(int amount) {

    }

    @Override
    public Heading getHeading() {
        return null;
    }

    @Override
    public void setHeading(Heading heading) {

    }

    @Override
    public Weapon getWeapon() {
        return null;
    }

    @Override
    public void setWeapon(Weapon weapon) {

    }

    @Override
    public Helmet getHelmet() {
        return null;
    }

    @Override
    public void setHelmet(Helmet helmet) {

    }

    @Override
    public Shield getShield() {
        return null;
    }

    @Override
    public void setShield(Shield shield) {

    }

    @Override
    public Armor getArmor() {
        return null;
    }

    @Override
    public void setArmor(Armor armor) {

    }

    @Override
    public Accessory getAccessory() {
        return null;
    }

    @Override
    public void setAccessory(Accessory accessory) {

    }

    @Override
    public Privileges getPrivileges() {
        return null;
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
        return charIndex;
    }

    @Override
    public void setCharIndex(int index) {
        this.charIndex = (short) index;
    }

    @Override
    public boolean isAdminHidden() {
        return false;
    }

    @Override
    public void setAdminHidden(boolean adminHidden) {

    }

    @Override
    public boolean isSailing() {
        return false;
    }

    @Override
    public void setSailing(boolean sailing) {

    }

    @Override
    public boolean isEquipped(Item item) {
        return false;
    }

    @Override
    public int getGold() {
        return 0;
    }

    @Override
    public Character getMaster() {
        return null;
    }

    @Override
    public NpcType getNPCType() {
        return null;
    }

    @Override
    public boolean isTameable() {
        return false;
    }

    @Override
    public boolean canTrade() {
        return false;
    }

    @Override
    public boolean isHostile() {
        return false;
    }

    @Override
    public boolean hasMaster() {
        return false;
    }

    public Npc getNpc() {
        return npc;
    }

}
