package com.ao.data.dao.ini;

import com.ao.data.dao.NPCCharacterPropertiesDAO;
import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.Alignment;
import com.ao.model.character.NPCType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.behavior.NullBehavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.character.movement.QuietMovementStrategy;
import com.ao.model.character.npc.Drop;
import com.ao.model.character.npc.drop.DropEverything;
import com.ao.model.character.npc.drop.Dropable;
import com.ao.model.character.npc.drop.RandomDrop;
import com.ao.model.character.npc.properties.*;
import com.ao.model.inventory.Inventory;
import com.ao.model.inventory.InventoryImpl;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.Item;
import com.ao.model.worldobject.WorldObjectType;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Ini-backed implementation of the World Object DAO.
 */

public class NPCPropertiesDAOIni implements NPCCharacterPropertiesDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NPCPropertiesDAOIni.class);

    private static final int MAX_SOUNDS = 3;

    private static final String INIT_HEADER = "INIT";
    private static final String NPC_SECTION_PREFIX = "NPC";
    private static final String NUM_NPCS_KEY = "num_npcs";

    private static final String NAME_KEY = "name";
    private static final String NPC_TYPE_ID_KEY = "npc_type_id";
    private static final String DESCRIPTION_KEY = "description";
    private static final String HEAD_KEY = "head";
    private static final String BODY_KEY = "body";
    private static final String HEADING_KEY = "heading";
    private static final String MOVEMENT_ID_KEY = "movement_id";
    private static final String ATTACKABLE_KEY = "attackable";
    private static final String RESPAWNABLE_KEY = "respawnable";
    private static final String HOSTILE_KEY = "hostile";
    private static final String TAMEABLE_KEY = "domable"; // Domable?
    private static final String ALIGNMENT_KEY = "alineacion";
    private static final String COMMERCIABLE_KEY = "comercia";
    private static final String ITEMS_TYPE_KEY = "TipoItems";
    private static final String ITEMS_AMOUNT_KEY = "NROITEMS";
    private static final String OBJECT_INVENTORY_PREFIX = "Obj";
    private static final String DROP_PREFIX = "Drop";
    private static final String CITY_KEY = "Ciudad";
    private static final String EXPERIENCE_KEY = "GiveEXP";
    private static final String GOLD_KEY = "GiveGLD";
    private static final String INVENTORY_RESPAWN_KEY = "InvReSpawn";
    private static final String MAX_HP_KEY = "MaxHp";
    private static final String MIN_HP_KEY = "MinHp";
    private static final String MAX_HIT_KEY = "MaxHIT";
    private static final String MIN_HIT_KEY = "MinHIT";
    private static final String DEFENSE_KEY = "def";
    private static final String SPELLS_AMOUNT_KEY = "LanzaSpells";
    private static final String SPELL_PREFIX = "SP";
    private static final String ATTACK_POWER_KEY = "PoderAtaque";
    private static final String DODGE_POWER_KEY = "PoderEvasion";
    private static final String CAN_WATER_KEY = "AguaValida";
    private static final String CAN_EARTH_KEY = "TierraInvalida";
    private static final String CAN_POISON_KEY = "Veneno";
    private static final String PARALYZABLE_KEY = "AfectaParalisis";
    private static final String SOUND_PREFIX = "SND";
    private static final String MAGIC_DEFENSE_KEY = "DefM";
    private static final String CREATURES_AMOUNT_KEY = "NroCriaturas";
    private static final String CREATURE_ID_PREFIX = "CI";
    private static final String CREATURE_NAME_PREFIX = "CN";
    private static final String ORIGINAL_POSITION_KEY = "PosOrig";


    private static final Map<LegacyNPCType, NPCType> npcTypeMapper;

    static {
        // Populate mappings from old object types to new ones
        npcTypeMapper = new HashMap<>();
        // Notice COMMON is not listed since it may be a merchant or a hostile npc
        npcTypeMapper.put(LegacyNPCType.DRAGON, NPCType.DRAGON);
        npcTypeMapper.put(LegacyNPCType.TRAINER, NPCType.TRAINER);
        npcTypeMapper.put(LegacyNPCType.GOVERNOR, NPCType.GOVERNOR);
        npcTypeMapper.put(LegacyNPCType.CHAOS_GUARD, NPCType.CHAOS_GUARD);
        npcTypeMapper.put(LegacyNPCType.ROYAL_GUARD, NPCType.ROYAL_GUARD);
        npcTypeMapper.put(LegacyNPCType.NOBLE, NPCType.NOBLE);
        npcTypeMapper.put(LegacyNPCType.NEWBIE_RESUCITATOR, NPCType.NEWBIE_RESUCITATOR);
        npcTypeMapper.put(LegacyNPCType.RESUCITATOR, NPCType.RESUCITATOR);
        npcTypeMapper.put(LegacyNPCType.GAMBLER, NPCType.GAMBLER);
        npcTypeMapper.put(LegacyNPCType.BANKER, NPCType.BANKER);
    }

    private final String npcsFilePath;

    private final WorldObjectPropertiesDAO worldObjectPropertiesDAO;
    private final WorldObjectFactory worldObjectFactory;

    private NPCProperties[] npcs;

    /**
     * Creates a new NPCDAOIni instance.
     *
     * @param npcsFilePath             path to the file with all objects definitions
     * @param worldObjectPropertiesDAO DAO for World Object Properties
     * @param worldObjectFactory       factory to create world object instances
     */
    @Inject
    public NPCPropertiesDAOIni(@Named("npcsFilePath") String npcsFilePath, WorldObjectPropertiesDAO worldObjectPropertiesDAO, WorldObjectFactory worldObjectFactory) {
        this.npcsFilePath = npcsFilePath;
        this.worldObjectPropertiesDAO = worldObjectPropertiesDAO;
        this.worldObjectFactory = worldObjectFactory;
    }

    @Override
    public NPCProperties[] loadAll() throws DAOException {
        INIConfiguration ini = null;
        LOGGER.info("Loading all NPCs from {}", npcsFilePath);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(npcsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + npcsFilePath + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
            LOGGER.info("NPCs loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading NPCs!", e);
            System.exit(-1);
        }

        // Required key
        int numNpcs = IniUtils.getRequiredInt(ini, INIT_HEADER + "." + NUM_NPCS_KEY);

        npcs = new NPCProperties[numNpcs];

        for (int i = 1; i < numNpcs; i++)
            npcs[i - 1] = loadNpc(i, ini);

        return npcs;
    }

    /**
     * Loads a npc.
     *
     * @param id  npc's id
     * @param ini ini configuration
     * @return new npc
     */
    private NPCProperties loadNpc(int id, INIConfiguration ini) {

        String section = NPC_SECTION_PREFIX + id;

        String name = IniUtils.getString(ini, section + "." + NAME_KEY, "");
        int npcTypeId = IniUtils.getInt(ini, section + "." + NPC_TYPE_ID_KEY, -1);

        short body = (short) IniUtils.getInt(ini, section + "." + BODY_KEY, 0);
        short head = getHead(ini, section);
        Heading heading = Heading.get((byte) (IniUtils.getInt(ini, section + "." + HEADING_KEY, 1) - 1));
        boolean respawnable = isRespawnable(ini, section);
        String description = getDescription(ini, section);

        // TODO Unir si es posible
        Class<? extends Behavior> behavior = getBehavior(id, ini, section);
        Class<? extends AttackStrategy> attackStrategy = getAttackStrategy(id, ini, section);
        Class<? extends MovementStrategy> movementStrategy = getMovementStrategy(id, ini, section);

        LegacyNPCType npcType = LegacyNPCType.findById(npcTypeId);

        if (npcType == null) {
            LOGGER.error("Unknown npc_type_id={} for NPC{}.", npcTypeId, id);
            return null;
        }

        NPCProperties npc = null;

        switch (npcType) {
            case COMMON:
                if (isCommerciable(ini, section))
                    npc = loadMerchant(NPCType.MERCHANT, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                else
                    npc = loadCreature(NPCType.COMMON, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case DRAGON:
            case PRETORIAN:
                npc = loadCreature(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case TRAINER:
                npc = loadTrainer(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case GOVERNOR:
                npc = loadGovernor(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case ROYAL_GUARD:
            case CHAOS_GUARD:
                npc = loadGuard(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case NOBLE:
                npc = loadNoble(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            case NEWBIE_RESUCITATOR:
            case RESUCITATOR:
            case GAMBLER:
            case BANKER:
                npc = loadBasicNpc(npcTypeMapper.get(npcType), id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
                break;
            default:
                LOGGER.error("Unexpected AI type found: {} for NPC with id {}", npcType, id);
        }

        return npc;
    }

    private NPCProperties loadNoble(NPCType type, int id, String name,
                                    short body, short head, Heading heading, boolean respawn,
                                    String desc, Class<? extends Behavior> behavior,
                                    Class<? extends AttackStrategy> attackStrategy,
                                    Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {
        Alignment alignment = getAlignment(ini, section);
        return new NobleNPCProperties(type, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, alignment);
    }

    /**
     * Creates a NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param desc
     * @param behavior
     * @param attackStrategy
     * @param movementStrategy
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadBasicNpc(NPCType type, int id, String name,
                                       short body, short head, Heading heading, boolean respawn,
                                       String desc, Class<? extends Behavior> behavior,
                                       Class<? extends AttackStrategy> attackStrategy,
                                       Class<? extends MovementStrategy> movementStrategy,
                                       INIConfiguration ini,
                                       String section) {
        return new NPCProperties(type, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy);
    }

    /**
     * Creates a Governor NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param movementStrategy
     * @param attackStrategy
     * @param behavior
     * @param desc
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadGovernor(NPCType type, int id, String name,
                                       short body, short head, Heading heading, boolean respawn,
                                       String desc, Class<? extends Behavior> behavior,
                                       Class<? extends AttackStrategy> attackStrategy,
                                       Class<? extends MovementStrategy> movementStrategy,
                                       INIConfiguration ini,
                                       String section) {
        City city = getCity(ini, section);
        return new GovernorNPCProperties(type, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, city);
    }

    /**
     * Creates a Merchant NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param movementStrategy
     * @param attackStrategy
     * @param behavior
     * @param desc
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadMerchant(NPCType type, int id, String name,
                                       short body, short head, Heading heading, boolean respawn,
                                       String desc, Class<? extends Behavior> behavior,
                                       Class<? extends AttackStrategy> attackStrategy,
                                       Class<? extends MovementStrategy> movementStrategy,
                                       INIConfiguration ini,
                                       String section) {

        Inventory inventory = getInventory(ini, section);

        boolean respawnInventory = hasInventoryRespawn(ini, section);
        Set<WorldObjectType> acceptedTypes = getItemsType(ini, section);

        return new MerchantNPCProperties(type, id, name, body, head, heading, respawn,
                behavior, attackStrategy, movementStrategy, desc, inventory,
                respawnInventory, acceptedTypes
        );
    }

    /**
     * Creates a Trainer NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param movementStrategy
     * @param attackStrategy
     * @param behavior
     * @param desc
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadTrainer(NPCType type, int id, String name, short body,
                                      short head, Heading heading, boolean respawn, String desc,
                                      Class<? extends Behavior> behavior,
                                      Class<? extends AttackStrategy> attackStrategy,
                                      Class<? extends MovementStrategy> movementStrategy,
                                      INIConfiguration ini,
                                      String section) {
        Map<Integer, String> creatures = getCreatures(ini, section);
        return new TrainerNPCProperties(type, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, creatures);
    }

    /**
     * Creates a Creature NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param movementStrategy
     * @param attackStrategy
     * @param behavior
     * @param desc
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadCreature(NPCType type, int id, String name, short body,
                                       short head, Heading heading, boolean respawn, String desc,
                                       Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                                       Class<? extends MovementStrategy> movementStrategy,
                                       INIConfiguration ini,
                                       String section) {

        int experience = getExperience(ini, section);
        int gold = getGold(ini, section);
        int minHP = getMinHP(ini, section);
        int maxHP = getMaxHP(ini, section);
        int minDamage = getMinDamage(ini, section);
        int maxDamage = getMaxDamage(ini, section);
        short defense = getDefense(ini, section);
        short magicDefense = getMagicDefense(ini, section);
        short accuracy = getAccuracy(ini, section);
        short dodge = getDodge(ini, section);
        List<Spell> spells = getSpells(ini, section);
        boolean canSwim = canSwim(ini, section);
        boolean canWalk = canWalk(ini, section);
        boolean attackable = isAttackable(ini, section);
        boolean poison = canPoison(ini, section);
        boolean paralyzable = isParalyzable(ini, section);
        boolean hostile = isHostile(ini, section);
        boolean tameable = isTameable(ini, section);
        Drop drops = getDrops(ini, section);

        return new CreatureNPCProperties(type, id, name, body, head, heading, respawn, desc,
                behavior, attackStrategy, movementStrategy, experience, gold, minHP, maxHP,
                minDamage, maxDamage, defense, magicDefense, accuracy, dodge, spells, canSwim, canWalk,
                attackable, poison, paralyzable, hostile, tameable, drops);
    }

    /**
     * Creates a Guard NPC's properties from the given section.
     *
     * @param type             the npc's type
     * @param id               the npc's id
     * @param name             the npc's name
     * @param body             the npc's body
     * @param head             the npc's head
     * @param heading          the npc's heading
     * @param respawn
     * @param movementStrategy
     * @param attackStrategy
     * @param behavior
     * @param desc
     * @param section          section of the ini file containing the world object to be loaded
     * @return the NPC created
     */
    private NPCProperties loadGuard(NPCType type, int id, String name, short body,
                                    short head, Heading heading, boolean respawn, String desc,
                                    Class<? extends Behavior> behavior,
                                    Class<? extends AttackStrategy> attackStrategy,
                                    Class<? extends MovementStrategy> movementStrategy,
                                    INIConfiguration ini,
                                    String section) {
        int experience = getExperience(ini, section);
        int gold = getGold(ini, section);
        int minHP = getMinHP(ini, section);
        int maxHP = getMaxHP(ini, section);
        int minDamage = getMinDamage(ini, section);
        int maxDamage = getMaxDamage(ini, section);
        short defense = getDefense(ini, section);
        short magicDefense = getMagicDefense(ini, section);
        short accuracy = getAccuracy(ini, section);
        short dodge = getDodge(ini, section);
        List<Spell> spells = getSpells(ini, section);
        boolean canSwim = canSwim(ini, section);
        boolean canWalk = canWalk(ini, section);
        boolean attackable = isAttackable(ini, section);
        boolean poison = canPoison(ini, section);
        boolean paralyzable = isParalyzable(ini, section);
        boolean hostile = isHostile(ini, section);
        boolean tameable = isTameable(ini, section);
        Drop drops = getDrops(ini, section);
        boolean originalPosition = hasOriginalPosition(ini, section);

        return new GuardNPCProperties(type, id, name, body, head, heading, respawn, desc,
                behavior, attackStrategy, movementStrategy, experience, gold, minHP, maxHP, minDamage,
                maxDamage, defense, magicDefense, accuracy, dodge, spells, canSwim, canWalk, attackable,
                poison, paralyzable, hostile, tameable, drops, originalPosition);
    }

    /**
     * Gets a npc's experience.
     *
     * @return the npc's experience
     */
    private int getExperience(INIConfiguration ini, String section) {
        String giveEXP = ini.getString(section + "." + EXPERIENCE_KEY);
        if (giveEXP == null) {
            LOGGER.warn("Missing 'giveEXP' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(giveEXP);
    }

    /**
     * Gets a npc's gold.
     *
     * @return the npc's gold
     */
    private int getGold(INIConfiguration ini, String section) {
        String giveGLD = ini.getString(section + "." + GOLD_KEY);
        if (giveGLD == null) {
            LOGGER.warn("Missing 'giveGLD' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(giveGLD);
    }

    /**
     * Gets a npc's min hp.
     *
     * @return the npc's min hp.
     */
    private int getMinHP(INIConfiguration ini, String section) {
        String minHp = ini.getString(section + "." + MIN_HP_KEY);
        if (minHp == null) {
            LOGGER.warn("Missing 'minHP' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(minHp);
    }

    /**
     * Gets a npc's max hp.
     *
     * @return the npc's max hp
     */
    private int getMaxHP(INIConfiguration ini, String section) {
        String maxHp = ini.getString(section + "." + MAX_HP_KEY);
        if (maxHp == null) {
            LOGGER.warn("Missing 'maxHP' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(maxHp);
    }

    /**
     * Gets a npc's min damage.
     *
     * @return the npc's min damage
     */
    private int getMinDamage(INIConfiguration ini, String section) {
        String minHIT = ini.getString(section + "." + MIN_HIT_KEY);
        if (minHIT == null) {
            LOGGER.warn("Missing 'minHIT' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(minHIT);
    }

    /**
     * Gets a npc's max damage
     *
     * @return the npc's max damage
     */
    private int getMaxDamage(INIConfiguration ini, String section) {
        String maxHIT = ini.getString(section + "." + MAX_HIT_KEY);
        if (maxHIT == null) {
            LOGGER.warn("Missing 'maxHIT' value for section: {}", section);
            return 0;
        }
        return Integer.parseInt(maxHIT);
    }

    /**
     * Gets a npc's defense
     *
     * @return the npc's defense
     */
    private short getDefense(INIConfiguration ini, String section) {
        String def = ini.getString(section + "." + DEFENSE_KEY);
        if (def == null) {
            LOGGER.warn("Missing 'def' value for section: {}", section);
            return 0;
        }
        return Short.parseShort(def);
    }

    /**
     * Gets a npc's magic defense
     *
     * @return the npc's magic defense
     */
    private short getMagicDefense(INIConfiguration ini, String section) {
        String defM = ini.getString(section + "." + MAGIC_DEFENSE_KEY);
        if (defM == null) {
            LOGGER.warn("Missing 'defM' value for section: {}", section);
            return 0;
        }
        return Short.parseShort(defM);
    }

    /**
     * Gets a npc's acc
     *
     * @return the npc's acc
     */
    private short getAccuracy(INIConfiguration ini, String section) {
        String poderAtaque = ini.getString(section + "." + ATTACK_POWER_KEY);
        if (poderAtaque == null) {
            LOGGER.warn("Missing 'poderAtaque' value for section: {}", section);
            return 0;
        }
        return Short.parseShort(poderAtaque);
    }

    /**
     * Gets a npc's dodge
     *
     * @return the npc's dodge
     */
    private short getDodge(INIConfiguration ini, String section) {
        String poderEvasion = ini.getString(section + "." + DODGE_POWER_KEY);
        if (poderEvasion == null) {
            LOGGER.warn("Missing 'poderEvasion' value for section: {}", section);
            return 0;
        }
        return Short.parseShort(poderEvasion);
    }

    /**
     * Gets a npc's spell amount
     *
     * @return the npc's spell amount
     */
    private byte getSpellAmount(INIConfiguration ini, String section) {
        String lanzaSpells = ini.getString(section + "." + SPELLS_AMOUNT_KEY);
        if (lanzaSpells == null) {
            LOGGER.warn("Missing 'lanzaSpells' value for section: {}", section);
            return 0;
        }
        return Byte.parseByte(lanzaSpells);
    }

    /**
     * Gets a npc's head from
     *
     * @return the npc's head or 0 if none was found
     */
    private short getHead(INIConfiguration ini, String section) {
        String head = ini.getString(section + "." + HEAD_KEY);
        if (head == null) {
            LOGGER.warn("Missing head value for section: {}", section);
            return 0;
        }
        try {
            return Short.parseShort(head);
        } catch (NumberFormatException e) {
            LOGGER.error("Error parsing head value for section {}: {}", section, e.getMessage());
            return 0;
        }
    }

    /**
     * Gets a list with the spells.
     *
     * @return the npc's spells
     */
    private List<Spell> getSpells(INIConfiguration ini, String section) {
        byte amount = getSpellAmount(ini, section);
        if (amount <= 0) return null;

        List<Spell> spells = null;
        String spell;
        for (byte i = 1; i <= amount; i++) {
            spell = ini.getString(SPELL_PREFIX + i);
            if (spell != null) {
                // TODO Implement Spells!
            }
        }
        return spells;
    }

    // ?
    private boolean canSwim(INIConfiguration ini, String section) {
        String aguaValida = ini.getString(section + "." + CAN_WATER_KEY);
        if (aguaValida == null) {
            LOGGER.warn("Missing 'aguaValida' value for section: {}", section);
            return false;
        }
        return !"0".equals(aguaValida);
    }

    // ?
    private boolean canWalk(INIConfiguration ini, String section) {
        String tierraInvalida = ini.getString(section + "." + CAN_EARTH_KEY);
        if (tierraInvalida == null) {
            LOGGER.warn("Missing 'tierraInvalida' value for section: {}", section);
            return false;
        }
        return !"0".equals(tierraInvalida);
    }

    /**
     * Checks if the npc is attackable.
     *
     * @return true if the npc is attackable, false otherwise
     */
    private boolean isAttackable(INIConfiguration ini, String section) {
        String attackable = ini.getString(section + "." + ATTACKABLE_KEY);
        if (attackable == null) {
            LOGGER.warn("Missing 'attackable' value for section: {}", section);
            return false;
        }
        return !"0".equals(attackable);
    }

    /**
     * Checks if the npc is hostile.
     *
     * @return true if the npc is hostile, false otherwise
     */
    private boolean isHostile(INIConfiguration ini, String section) {
        String hostile = ini.getString(section + "." + HOSTILE_KEY);
        if (hostile == null) {
            LOGGER.warn("Missing 'hostile' value for section: {}", section);
            return false;
        }
        return !"0".equals(hostile);
    }

    /**
     * Checks if the section corresponds to a tameable npc.
     *
     * @return true if the item is tameable, false otherwise
     */
    private boolean isTameable(INIConfiguration ini, String section) {
        String domable = ini.getString(section + "." + TAMEABLE_KEY);
        if (domable == null) {
            LOGGER.warn("Missing 'domable' value for section: {}", section);
            return false;
        }
        return !"0".equals(domable);
    }

    /**
     * Checks if the npc can poison.
     *
     * @return true if the npc can poison, false otherwise
     */
    private boolean canPoison(INIConfiguration ini, String section) {
        String veneno = ini.getString(section + "." + CAN_POISON_KEY);
        if (veneno == null) {
            LOGGER.warn("Missing 'veneno' value for section: {}", section);
            return false;
        }
        return !"0".equals(veneno);
    }

    /**
     * Checks if the npc has an original position.
     *
     * @return true if the npc has the original position, false otherwise
     */
    private boolean hasOriginalPosition(INIConfiguration ini, String section) {
        String posOrig = ini.getString(section + "." + ORIGINAL_POSITION_KEY);
        if (posOrig == null) {
            LOGGER.warn("Missing 'posOrig' value for section: {}", section);
            return false;
        }
        return !"0".equals(posOrig);
    }

    /**
     * Checks if the npc is paralyzable.
     *
     * @return true if the npc is paralyzable, false otherwise
     */
    private boolean isParalyzable(INIConfiguration ini, String section) {
        String afectaParalisis = ini.getString(section + "." + PARALYZABLE_KEY);
        if (afectaParalisis == null) {
            LOGGER.warn("Missing 'afectaParalisis' value for section: {}", section);
            return false;
        }
        return !"0".equals(afectaParalisis);
    }

    /**
     * Generates a drop logic object based on the configuration specified in an INI file section. This method determines how items
     * are dropped by NPCs based on predefined configurations.
     *
     * @return a {@link Drop} object that contains the drop logic for the NPC, or null if configuration is invalid or missing
     */
    private Drop getDrops(INIConfiguration ini, String section) {
        String nroItems = ini.getString(section + "." + ITEMS_AMOUNT_KEY);
        if (nroItems == null) {
            LOGGER.warn("Missing 'nroItems' value for section: {}", section);
            return null;
        }

        // Is it a Pretorian NPC?
        String movement = ini.getString(MOVEMENT_ID_KEY);

        if (movement == null) {
            LOGGER.warn("Missing 'movement' value for section: {}", section);
            return null;
        }

        // Pretorian NPCs drop everything... a little bit hard coded, but we want to be compatible with old AO
        LegacyAIType aiType = LegacyAIType.findById(Integer.parseInt(movement));
        if (aiType.isPretorian()) return new DropEverything(getInventory(ini, section));

        int count = Integer.parseInt(nroItems);

        List<Dropable> dropables = new LinkedList<>();
        String slot;

        /*
         * In Argentum, there is a 10% chance of dropping nothing; the other 90%
         * is split among the count items in such a way that each has 10% the
         * chance of the previous one.
         */
        float chance = 0.9f;
        for (int i = 1; i <= count; i++) {
            slot = ini.getString(DROP_PREFIX + i);
            if (slot != null) {
                String[] slotInfo = slot.split("-");
                // In every step except the last one, leave a 10% chance for the next level
                float curChance = i == count ? chance : chance * 0.9f;
                dropables.add(new Dropable(Integer.parseInt(slotInfo[0]), Integer.parseInt(slotInfo[1]), curChance));
            }

            // The chance for the next step is 10% of the current one
            chance *= 0.1f;
        }

        if (!dropables.isEmpty()) return new RandomDrop(dropables);

        return null;
    }

    // TODO Use this!

    /**
     * Gets a list of sound for all npc's.
     *
     * @return a list of sound for all npc's
     */
    private List<Integer> getSounds(INIConfiguration ini) {
        List<Integer> sounds = new LinkedList<>();
        String sound;
        for (int i = 1; i <= MAX_SOUNDS; i++) {
            sound = ini.getString(SOUND_PREFIX + i);
            if (sound != null) sounds.add(Integer.parseInt(sound));
        }
        return sounds;
    }

    /**
     * Gets the inventory configuration for a specific section from the provided INI configuration. The method parses the
     * configuration file to create an inventory object by reading the items and their amounts associated with the specified
     * section.
     *
     * @return an Inventory object populated with the specified items from the given section, or null if the required
     * configuration is missing or invalid
     */
    private Inventory getInventory(INIConfiguration ini, String section) {
        String nroItems = ini.getString(section + "." + ITEMS_AMOUNT_KEY);
        if (nroItems == null) {
            LOGGER.warn("Missing 'nroItems' value for section: {}", section);
            return null;
        }

        String slot;
        byte inventorySize = Byte.parseByte(nroItems);

        Inventory inventory = new InventoryImpl(inventorySize);

        for (byte i = 1; i <= inventorySize; i++) {
            slot = ini.getString(OBJECT_INVENTORY_PREFIX + i);
            if (slot != null) {
                String[] slotInfo = slot.split("-");
                int objId = Integer.parseInt(slotInfo[0]);
                int amount = Integer.parseInt(slotInfo[1]);
                WorldObjectProperties woProperties = worldObjectPropertiesDAO.getWorldObjectProperties(objId);
                Item worldObject;
                try {
                    worldObject = worldObjectFactory.getWorldObject(woProperties, amount);
                } catch (WorldObjectFactoryException e) {
                    LOGGER.warn("An NPC has an item in it's inventory that can't be created. Object id: {}. Ignoring it...", objId, e);
                    continue;
                }
                if (worldObject != null) inventory.addItem(worldObject);
                else
                    LOGGER.error("An NPC has the object with id {} in it's inventory, but the object is not an item. Ignoring it...", objId);
            }
        }

        return inventory;
    }

    /**
     * Gets a map of creatures where the key is the creature ID and the value is the creature name. The creature information is
     * parsed from the specified section of the provided INIConfiguration.
     *
     * @return a map containing creature IDs as keys and their corresponding names as values or null if the configuration is
     * incomplete or missing
     */
    private Map<Integer, String> getCreatures(INIConfiguration ini, String section) {
        String nroCriaturas = ini.getString(section + "." + CREATURES_AMOUNT_KEY);
        if (nroCriaturas == null) {
            LOGGER.warn("Missing 'nroCriaturas' value for section: {}", section);
            return null;
        }
        String creatureId, creatureName;
        Map<Integer, String> creatures = null;
        for (int i = 1; i <= Integer.parseInt(nroCriaturas); i++) {
            creatureId = ini.getString(CREATURE_ID_PREFIX + i);
            creatureName = ini.getString(CREATURE_NAME_PREFIX + i);
            if (creatureId != null && creatureName != null) {
                if (null == creatures) creatures = new HashMap<>();
                creatures.put(Integer.parseInt(creatureId), creatureName);
            }
        }
        return creatures;
    }

    /**
     * Gets npc's alignment.
     *
     * @return the npc's alignment
     */
    private Alignment getAlignment(INIConfiguration ini, String section) {
        String alineacion = ini.getString(section + "." + ALIGNMENT_KEY);
        if (alineacion == null) {
            LOGGER.warn("Missing 'alineacion' value for section: {}", section);
            return null;
        }
        if (!"0".equals(alineacion.trim())) return Alignment.CRIMINAL;
        return Alignment.CITIZEN;
    }

    /**
     * Gets a set of item types based on the specified section and configuration.
     *
     * @return a set of WorldObjectType representing the types of items defined in the given section, or null if the type
     * specification is missing
     */
    private Set<WorldObjectType> getItemsType(INIConfiguration ini, String section) {
        String tipoItems = ini.getString(section + "." + ITEMS_TYPE_KEY);
        if (tipoItems == null) {
            LOGGER.warn("Missing 'tipoItems' value for section: {}", section);
            return null;
        }
        Set<WorldObjectType> acceptedTypes;
        LegacyWorldObjectType objectType = LegacyWorldObjectType.findById(Integer.parseInt(tipoItems));
        if (objectType == null) {
            // All item types are accepted
            acceptedTypes = new HashSet<>();
            acceptedTypes.addAll(Arrays.asList(WorldObjectType.values()));
        } else acceptedTypes = objectType.getPlausibleCurrentTypes();
        return acceptedTypes;
    }

    /**
     * Checks if the specified section in the INI configuration is marked as {@code InvReSpawn}.
     *
     * @return true if the section is marked as {@code InvReSpawn} and not equal to "0", false otherwise
     */
    private boolean hasInventoryRespawn(INIConfiguration ini, String section) {
        String invRespawn = ini.getString(section + "." + INVENTORY_RESPAWN_KEY);
        if (invRespawn == null) {
            LOGGER.warn("Missing 'invRespawn' value for section: {}", section);
            return false;
        }
        return !"0".equals(invRespawn.trim());
    }

    private boolean isRespawnable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RESPAWNABLE_KEY, true);
    }

    /**
     * Gets npc's city.
     *
     * @return the npc's city
     */
    private City getCity(INIConfiguration ini, String section) {
        String ciudad = ini.getString(section + "." + CITY_KEY);
        if (ciudad == null) {
            LOGGER.warn("Missing 'ciudad' value for section: {}", section);
            return null;
        }
        // TODO Implementar Cities :d
        return null;
    }

    /**
     * Checks if the specified section in the INI configuration is marked as {@code comercia}.
     *
     * @return true if the section is marked as {@code comercia} and not equal to "0", false otherwise
     */
    private boolean isCommerciable(INIConfiguration ini, String section) {
        String comercia = ini.getString(section + "." + COMMERCIABLE_KEY);
        if (comercia == null) {
            LOGGER.warn("Missing 'comercia' value for section: {}", section);
            return false;
        }
        return !"0".equals(comercia.trim());
    }

    /**
     * Gets npc's description.
     *
     * @return the npc's description, empty string otherwise
     */
    private String getDescription(INIConfiguration ini, String section) {
        return IniUtils.getString(ini, section + "." + DESCRIPTION_KEY, "");
    }

    /**
     * Gets npc's behavior.
     *
     * @return the npc's behavior, null if no behavior is specified
     */
    private Class<? extends Behavior> getBehavior(int id, INIConfiguration ini, String section) {
        int movementId = IniUtils.getInt(ini, section + "." + MOVEMENT_ID_KEY, 0);
        LegacyAIType aiType = LegacyAIType.findById(movementId);
        if (aiType == null) {
            LOGGER.warn("Unknown movement_id={} for OBJ{}.", movementId, id);
            return null;
        }
        return aiType.getBehavior();
    }

    /**
     * Gets npc's attackStrategy.
     *
     * @return the npc's attack strategy
     */
    private Class<? extends AttackStrategy> getAttackStrategy(int id, INIConfiguration ini, String section) {
        int movementId = IniUtils.getInt(ini, section + "." + MOVEMENT_ID_KEY, 0);
        LegacyAIType aiType = LegacyAIType.findById(movementId);
        if (aiType == null) {
            LOGGER.warn("Unknown movement_id={} for OBJ{}.", movementId, id);
            return null;
        }
        return aiType.getAttackStrategy();
    }

    /**
     * Gets npc's movementStrategy.
     *
     * @return the npc's movement strategy
     */
    private Class<? extends MovementStrategy> getMovementStrategy(int id, INIConfiguration ini, String section) {
        int movementId = IniUtils.getInt(ini, section + "." + MOVEMENT_ID_KEY, 0);
        LegacyAIType aiType = LegacyAIType.findById(movementId);
        if (aiType == null) {
            LOGGER.warn("Unknown movement_id={} for OBJ{}.", movementId, id);
            return null;
        }
        return aiType.getMovementStrategy();
    }

    /**
     * NPC Type enumeration, as it was known in the old days of Visual Basic.
     */
    private enum LegacyNPCType {

        COMMON(0), // TODO No deberia empezar en 1?
        RESUCITATOR(1),
        ROYAL_GUARD(2),
        TRAINER(3),
        BANKER(4),
        NOBLE(5),
        DRAGON(6),
        GAMBLER(7),
        CHAOS_GUARD(8),
        NEWBIE_RESUCITATOR(9),
        PRETORIAN(10),
        GOVERNOR(11);

        private final int id;

        /**
         * Creates a new LegacyWorldObjectType.
         *
         * @param id value corresponding to the object type. Should be unique
         */
        LegacyNPCType(int id) {
            this.id = id;
        }

        public static LegacyNPCType findById(int id) {
            for (LegacyNPCType type : LegacyNPCType.values())
                if (type.id == id) return type;
            return null;
        }

    }

    /**
     * Enum with legacy NPCTypes, which are now useless.
     */
    private enum LegacyAIType {
        // TODO Complete this as we code the behaviors!
        STATIC(1, NullBehavior.class, null, QuietMovementStrategy.class),
        RANDOM(2, null, null, null),
        BAD_ATTACKS_GOOD(3, null, null, null),
        DEFENSIVE(4, null, null, null),
        GUARD_ATTACK_CRIMINALS(5, null, null, null),
        NPC_OBJECT(6, null, null, QuietMovementStrategy.class),
        FOLLW_MASTER(8, null, null, null),
        ATTACK_NPC(9, null, null, null),
        PATHFINDING(10, null, null, null),
        PRETORIAN_PRIEST(20, null, null, null),
        PRETORIAN_WARRIOR(21, null, null, null),
        PRETORIAN_MAGE(22, null, null, null),
        PRETORIAN_HUNTER(23, null, null, null),
        PRETORIAN_KING(24, null, null, null);

        private final int id;
        private final Class<? extends Behavior> behavior;
        private final Class<? extends AttackStrategy> attackStrategy;
        private final Class<? extends MovementStrategy> movementStrategy;

        /**
         * @param id               numeric value associated with the AI Type
         * @param behavior         behavior class to be used
         * @param attackStrategy   attack strategy class to be used
         * @param movementStrategy movement strategy class to be used
         */
        LegacyAIType(int id, Class<? extends Behavior> behavior,
                     Class<? extends AttackStrategy> attackStrategy,
                     Class<? extends MovementStrategy> movementStrategy) {
            this.id = id;
            this.behavior = behavior;
            this.attackStrategy = attackStrategy;
            this.movementStrategy = movementStrategy;
        }

        public static LegacyAIType findById(int id) {
            for (LegacyAIType aiType : LegacyAIType.values())
                if (aiType.id == id) return aiType;
            return null;
        }

        public Class<? extends Behavior> getBehavior() {
            return behavior;
        }

        public Class<? extends AttackStrategy> getAttackStrategy() {
            return attackStrategy;
        }

        public Class<? extends MovementStrategy> getMovementStrategy() {
            return movementStrategy;
        }

        /**
         * Checks if the current NPC is pretorian
         *
         * @return true if the NPC is pretorian, false otherwise
         */
        public boolean isPretorian() {
            return this == PRETORIAN_HUNTER || this == PRETORIAN_KING || this == PRETORIAN_MAGE || this == PRETORIAN_PRIEST || this == PRETORIAN_WARRIOR;
        }

    }

}
