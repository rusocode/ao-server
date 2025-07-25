package com.ao.model.builder;

import com.ao.model.character.*;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.inventory.Inventory;
import com.ao.model.map.City;
import com.ao.model.map.Position;
import com.ao.model.spell.Spell;
import com.ao.model.user.ConnectedUser;
import com.ao.model.user.Guild;
import com.ao.model.user.LoggedUser;
import com.ao.service.ValidatorService;

import javax.management.InvalidAttributeValueException;
import java.util.Map;
import java.util.Objects;

public class UserCharacterBuilder implements Builder<UserCharacter> {

    public static final String INVALID_NAME_ERROR = "El nombre ingresado no es válido.";
    public static final String INVALID_EMAIL_ERROR = "La dirección de e-mail ingresada no es válida.";

    protected String name;
    protected String email;
    protected Race race;
    protected Gender gender;
    protected ConnectedUser user;
    protected City homeland;
    protected UserArchetype archetype;

    protected Integer minHp;
    protected Integer maxHp;
    protected Integer minMana;
    protected Integer maxMana;


    protected Integer head;
    protected Integer body;
    protected Map<Skill, Byte> skills;
    protected boolean paralyzed = false;
    protected boolean dumbed = false;
    protected String description;
    protected boolean hidden = false;
    protected boolean mimetized = false;
    protected boolean immobilized = false;
    protected boolean invisible = false;
    protected byte lvl = 1;
    protected boolean poisoned = false;
    protected Guild guild;
    protected Long exp;
    protected int maxThirstiness = 100;
    protected int maxHunger = 100;
    protected int minThirstiness = 100;
    protected int minHunger = 100;
    protected Inventory inventory;
    protected Spell[] spells;
    protected Reputation reputation;
    protected Position position;


    public UserCharacterBuilder withCity(City homeland) {
        this.homeland = Objects.requireNonNull(homeland);
        return this;
    }

    public UserCharacterBuilder withRace(Race race) {
        this.race = Objects.requireNonNull(race);
        return this;
    }

    public UserCharacterBuilder withParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
        return this;
    }

    public UserCharacterBuilder withPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
        return this;
    }

    public UserCharacterBuilder withImmobilized(boolean immobilized) {
        this.immobilized = immobilized;
        return this;
    }

    public UserCharacterBuilder withInvisible(boolean invisible) {
        this.invisible = invisible;
        return this;
    }

    public UserCharacterBuilder withDumbed(boolean dumbed) {
        this.dumbed = dumbed;
        return this;
    }

    public UserCharacterBuilder withHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public UserCharacterBuilder withMimetized(boolean mimetized) {
        this.mimetized = mimetized;
        return this;
    }

    public UserCharacterBuilder withLvl(byte lvl) {
        this.lvl = lvl;
        return this;
    }

    public UserCharacterBuilder withThirsthiness(int minThirstiness, int maxThirstiness) {
        this.minThirstiness = minThirstiness;
        this.maxThirstiness = maxThirstiness;
        return this;
    }


    public UserCharacterBuilder withHunger(int minHunger, int maxHunger) {
        this.minHunger = minHunger;
        this.maxHunger = maxHunger;
        return this;
    }

    public UserCharacterBuilder withDescription(String description) {
        if (!ValidatorService.validCharacterName(name)) throw new IllegalArgumentException();
        this.description = description;
        return this;

    }

    public UserCharacterBuilder withGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender);
        return this;
    }


    public UserCharacterBuilder withName(String name) {
        if (!ValidatorService.validCharacterName(name)) throw new IllegalArgumentException(INVALID_NAME_ERROR);
        this.name = name;
        return this;
    }

    public UserCharacterBuilder withEmail(String email) {
        if (!ValidatorService.validEmail(email)) throw new IllegalArgumentException(INVALID_EMAIL_ERROR);
        this.email = email;
        return this;
    }

    public UserCharacterBuilder withArchetype(UserArchetype archetype) {
        this.archetype = Objects.requireNonNull(archetype);
        return this;
    }

    public UserCharacterBuilder withHead(int head) {
        if (head < 0) throw new IllegalArgumentException();
        this.head = head;
        return this;
    }

    public UserCharacterBuilder withBody(int body) {
        if (body < 0) throw new IllegalArgumentException();
        this.body = body;
        return this;
    }

    public UserCharacterBuilder withConnectedUser(ConnectedUser user) {
        this.user = Objects.requireNonNull(user);
        return this;
    }

    public UserCharacterBuilder withHp(int minHp, int maxHp) {
        if (maxHp < 0 || minHp > maxHp) throw new IllegalArgumentException();
        this.minHp = minHp;
        this.maxHp = maxHp;
        return this;
    }

    public UserCharacterBuilder withMana(int minMana, int maxMana) {
        if (maxMana < 0 || minMana > maxMana) throw new IllegalArgumentException();
        this.minMana = minMana;
        this.maxMana = maxMana;
        return this;
    }

    public UserCharacterBuilder withSkills(Map<Skill, Byte> skills) throws InvalidAttributeValueException {
        for (Skill skill : Skill.VALUES) {
            if (skills.containsKey(skill)) Objects.requireNonNull(skills.get(skill));
            else throw new InvalidAttributeValueException();
        }
        this.skills = skills;
        return this;
    }

    public UserCharacterBuilder withGuild(Guild guild) {
        this.guild = guild;
        return this;
    }

    public UserCharacterBuilder withExp(long exp) {
        this.exp = exp;
        return this;
    }

    public UserCharacterBuilder withInventory(Inventory inventory) {
        this.inventory = Objects.requireNonNull(inventory);
        return this;
    }

    public UserCharacterBuilder withSpells(Spell[] spells) throws InvalidAttributeValueException {
        for (Spell spell : spells)
            if (spell == null) throw new InvalidAttributeValueException();
        this.spells = spells;
        return this;
    }

    public UserCharacterBuilder withReputation(Reputation reputation) {
        this.reputation = Objects.requireNonNull(reputation);
        return this;
    }

    public UserCharacterBuilder withPosition(Position position) {
        this.position = Objects.requireNonNull(position);
        return this;
    }


    @Override
    public UserCharacter build() {
        Objects.requireNonNull(user);
        Objects.requireNonNull(maxHp);
        Objects.requireNonNull(minHp);
        Objects.requireNonNull(maxMana);
        Objects.requireNonNull(minMana);
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(race);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(user);
        Objects.requireNonNull(homeland);
        Objects.requireNonNull(archetype);
        Objects.requireNonNull(head);
        Objects.requireNonNull(body);
        Objects.requireNonNull(skills);
        Objects.requireNonNull(description);
        Objects.requireNonNull(guild);
        Objects.requireNonNull(exp);
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(spells);
        Objects.requireNonNull(reputation);
        Objects.requireNonNull(position);

        //TODO Set everything!

        return new LoggedUser(user, reputation, race, gender, archetype.getArchetype(), poisoned,
                paralyzed, immobilized, mimetized, invisible, dumbed, hidden, maxMana, minMana, maxHp, minHp,
                maxThirstiness, minThirstiness, maxHunger, minHunger, lvl, name, description);
    }

}
