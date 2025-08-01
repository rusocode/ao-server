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
import com.ao.model.worldobject.WorldObject;
import com.ao.model.worldobject.WorldObjectType;
import com.ao.model.worldobject.factory.WorldObjectFactory;
import com.ao.model.worldobject.factory.WorldObjectFactoryException;
import com.ao.model.worldobject.properties.WorldObjectProperties;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Ini-backed implementation of the World Object DAO.
 */

public class NPCPropertiesDAOIni implements NPCCharacterPropertiesDAO {

    private static final Logger logger = LoggerFactory.getLogger(NPCPropertiesDAOIni.class);

    private static final int MAX_SOUNDS = 3;

    private static final String INIT_HEADER = "INIT";
    private static final String NUM_NPCS_KEY = "NumNPCs";

    private static final String NPC_SECTION_PREFIX = "NPC";
    private static final String NAME_KEY = "Name";
    private static final String NPC_TYPE_KEY = "NpcType";
    private static final String DESCRIPTION_KEY = "Desc";
    private static final String HEAD_KEY = "Head";
    private static final String BODY_KEY = "Body";
    private static final String HEADING_KEY = "Heading";
    private static final String MOVEMENT_KEY = "Movement";
    private static final String ATTACKABLE_KEY = "Attackable";
    private static final String RESPAWNABLE_KEY = "Respawn";
    private static final String HOSTILE_KEY = "Hostile";
    private static final String TAMEABLE_KEY = "Domable";
    private static final String ALIGNMENT_KEY = "Alineacion";
    private static final String COMMERCIABLE_KEY = "Comercia";
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

    /**
     * Creates a new NPCDAOIni instance.
     *
     * @param npcsFilePath             path to the file with all objects definitions
     * @param worldObjectPropertiesDAO DAO for World Object Properties
     * @param worldObjectFactory       factory to create world object instances
     */
    @Inject
    public NPCPropertiesDAOIni(@Named("npcsFilePath") String npcsFilePath,
                               WorldObjectPropertiesDAO worldObjectPropertiesDAO, WorldObjectFactory worldObjectFactory) {
        this.npcsFilePath = npcsFilePath;
        this.worldObjectPropertiesDAO = worldObjectPropertiesDAO;
        this.worldObjectFactory = worldObjectFactory;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.data.dao.NPCDAO#retrieveAll()
     */
    @Override
    public NPCProperties[] retrieveAll() throws DAOException {
        Ini iniFile;

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(npcsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + npcsFilePath + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            iniFile = new Ini(reader);
        } catch (Exception e) {
            logger.error("NPC loading failed!", e);
            throw new RuntimeException(e);
        }

        int totalNpcs = Integer.parseInt(iniFile.get(INIT_HEADER, NUM_NPCS_KEY));

        NPCProperties[] npcs = new NPCProperties[totalNpcs];

        for (int i = 1; i < totalNpcs; i++)
            npcs[i - 1] = loadNpc(i, iniFile);

        return npcs;
    }

    /**
     * Creates an object's properties from the given section.
     *
     * @param iniFile ini file that contains all world object to be loaded.
     * @return the world object created.
     */
    private NPCProperties loadNpc(int id, Ini iniFile) {

        // The section of the ini file containing the world object to be loaded.
        Section section = iniFile.get(NPC_SECTION_PREFIX + id);

        // Make sure it's valid
        if (section == null) return null;

        NPCProperties npc = null;
        String name = section.get(NAME_KEY);
        short body = Short.parseShort(section.get(BODY_KEY));
        short head = getHead(section);
        Heading heading = Heading.get((byte) (Byte.parseByte(section.get(HEADING_KEY)) - 1));
        boolean respawn = hasRespawn(section);
        String desc = getDescription(section);
        Class<? extends Behavior> behavior = getBehavior(section);
        Class<? extends AttackStrategy> attackStrategy = getAttackStrategy(section);
        Class<? extends MovementStrategy> movementStrategy = getMovementStrategy(section);

        LegacyNPCType type = getNpcType(section);

        switch (type) {
            case COMMON:
                if (isCommerciable(section))
                    npc = loadMerchant(NPCType.MERCHANT, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                else
                    npc = loadCreature(NPCType.COMMON, id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case DRAGON:
            case PRETORIAN:
                npc = loadCreature(npcTypeMapper.get(type), id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case TRAINER:
                npc = loadTrainer(npcTypeMapper.get(type), id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case GOVERNOR:
                npc = loadGovernor(npcTypeMapper.get(type), id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case ROYAL_GUARD:
            case CHAOS_GUARD:
                npc = loadGuard(npcTypeMapper.get(type), id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case NOBLE:
                npc = loadNoble(npcTypeMapper.get(type), id, name, body, head, heading, respawn, desc, behavior, attackStrategy, movementStrategy, section);
                break;
            case NEWBIE_RESUCITATOR:
            case RESUCITATOR:
            case GAMBLER:
            case BANKER:
                npc = loadBasicNpc(npcTypeMapper.get(type), id, name, body, head, heading, respawn,
                        desc, behavior, attackStrategy, movementStrategy, section);
                break;
            default:
                logger.error("Unexpected AI type found: " + type + " for NPC with id " + id);
        }
        return npc;
    }

    private NPCProperties loadNoble(NPCType type, int id, String name,
                                    short body, short head, Heading heading, boolean respawn,
                                    String desc, Class<? extends Behavior> behavior,
                                    Class<? extends AttackStrategy> attackStrategy,
                                    Class<? extends MovementStrategy> movementStrategy, Section section) {
        Alignment alignment = getAlignment(section);
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
                                       Section section) {
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
                                       Section section) {
        City city = getCity(section);
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
                                       Section section) {

        Inventory inventory = getInventory(section);

        boolean respawnInventory = hasInventoryRespawn(section);
        Set<WorldObjectType> acceptedTypes = getItemsType(section);

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
                                      Section section) {
        Map<Integer, String> creatures = getCreatures(section);
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
                                       Section section) {

        int experience = getExperience(section);
        int gold = getGold(section);
        int minHP = getMinHP(section);
        int maxHP = getMaxHP(section);
        int minDamage = getMinDamage(section);
        int maxDamage = getMaxDamage(section);
        short defense = getDefense(section);
        short magicDefense = getMagicDefense(section);
        short accuracy = getAccuracy(section);
        short dodge = getDodge(section);
        List<Spell> spells = getSpells(section);
        boolean canSwim = canSwim(section);
        boolean canWalk = canWalk(section);
        boolean attackable = isAttackable(section);
        boolean poison = canPoison(section);
        boolean paralyzable = isParalyzable(section);
        boolean hostile = isHostile(section);
        boolean tameable = isTameable(section);
        Drop drops = getDrops(section);

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
                                    Section section) {
        int experience = getExperience(section);
        int gold = getGold(section);
        int minHP = getMinHP(section);
        int maxHP = getMaxHP(section);
        int minDamage = getMinDamage(section);
        int maxDamage = getMaxDamage(section);
        short defense = getDefense(section);
        short magicDefense = getMagicDefense(section);
        short accuracy = getAccuracy(section);
        short dodge = getDodge(section);
        List<Spell> spells = getSpells(section);
        boolean canSwim = canSwim(section);
        boolean canWalk = canWalk(section);
        boolean attackable = isAttackable(section);
        boolean poison = canPoison(section);
        boolean paralyzable = isParalyzable(section);
        boolean hostile = isHostile(section);
        boolean tameable = isTameable(section);
        Drop drops = getDrops(section);
        boolean originalPosition = hasOriginalPosition(section);

        return new GuardNPCProperties(type, id, name, body, head, heading, respawn, desc,
                behavior, attackStrategy, movementStrategy, experience, gold, minHP, maxHP, minDamage,
                maxDamage, defense, magicDefense, accuracy, dodge, spells, canSwim, canWalk, attackable,
                poison, paralyzable, hostile, tameable, drops, originalPosition);
    }

    /**
     * Retrieves a npc's type from its section.
     *
     * @param section section from which to read the npc type
     * @return the npc's type
     */
    private LegacyNPCType getNpcType(Section section) {
        String data = section.get(NPC_TYPE_KEY);
        int npcType = 0;
        if (data != null) npcType = Integer.parseInt(data);
        return LegacyNPCType.valueOf(npcType);
    }

    /**
     * Retrieves a npc's experience from its section.
     *
     * @param section section from which to read the npc experience
     * @return the npc's experience
     */
    private int getExperience(Section section) {
        String data = section.get(EXPERIENCE_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's gold from its section.
     *
     * @param section section from which to read the npc's gold
     * @return the npc's gold
     */
    private int getGold(Section section) {
        String data = section.get(GOLD_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's min hp from its section.
     *
     * @param section section from which to read the npc's min hp.
     * @return the npc's min hp.
     */
    private int getMinHP(Section section) {
        String data = section.get(MIN_HP_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's max hp from its section.
     *
     * @param section section from which to read the npc's max hp
     * @return the npc's max hp
     */
    private int getMaxHP(Section section) {
        String data = section.get(MAX_HP_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's min damage from its section.
     *
     * @param section section from which to read the npc's min damage
     * @return the npc's min damage
     */
    private int getMinDamage(Section section) {
        String data = section.get(MIN_HIT_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's max damage from its section.
     *
     * @param section section from which to read the npc's max damage
     * @return the npc's max damage
     */
    private int getMaxDamage(Section section) {
        String data = section.get(MAX_HIT_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves a npc's defense from its section.
     *
     * @param section section from which to read the npc's defense
     * @return the npc's defense
     */
    private short getDefense(Section section) {
        String data = section.get(DEFENSE_KEY);
        if (data == null) return 0;
        return Short.parseShort(data);
    }

    /**
     * Retrieves a npc's magic defense from its section.
     *
     * @param section section from which to read the npc's magic defense
     * @return the npc's magic defense
     */
    private short getMagicDefense(Section section) {
        String data = section.get(MAGIC_DEFENSE_KEY);
        if (data == null) return 0;
        return Short.parseShort(data);
    }

    /**
     * Retrieves a npc's acc from its section.
     *
     * @param section section from which to read the npc's acc
     * @return the npc's acc
     */
    private short getAccuracy(Section section) {
        String data = section.get(ATTACK_POWER_KEY);
        if (data == null) return 0;
        return Short.parseShort(data);
    }

    /**
     * Retrieves a npc's dodge from its section.
     *
     * @param section section from which to read the npc's dodge
     * @return the npc's dodge
     */
    private short getDodge(Section section) {
        String data = section.get(DODGE_POWER_KEY);
        if (data == null) return 0;
        return Short.parseShort(data);
    }

    /**
     * Retrieves a npc's spell amount from its section.
     *
     * @param section section from which to read the npc's spell amount
     * @return the npc's spell amount
     */
    private byte getSpellAmount(Section section) {
        String data = section.get(SPELLS_AMOUNT_KEY);
        if (data == null) return 0;
        return Byte.parseByte(data);
    }

    /**
     * Retrieves a npc's head from its section.
     *
     * @param section section from which to read the npc's head
     * @return the npc's head
     */
    private short getHead(Section section) {
        String data = section.get(HEAD_KEY);
        if (data == null) return 0;
        return Short.parseShort(data);
    }

    /**
     * Retrieves a list with the spells from its section.
     *
     * @param section section for the npc
     * @return the npc's spells
     */
    private List<Spell> getSpells(Section section) {
        byte amount = getSpellAmount(section);
        if (amount <= 0) return null;

        List<Spell> spells = null;
        String spell;
        for (byte i = 1; i <= amount; i++) {
            spell = section.get(SPELL_PREFIX + i);
            if (spell != null) {
                // TODO Implement Spells!
            }
        }
        return spells;
    }

    /**
     * Checks if the npc.
     *
     * @param section section for the item to check
     * @return true if the item is a ranged weapon, false otherwise
     */
    private boolean canSwim(Section section) {
        String data = section.get(CAN_WATER_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the npc.
     *
     * @param section section for the item to check
     * @return true if the item is a ranged weapon, false otherwise
     */
    private boolean canWalk(Section section) {
        String data = section.get(CAN_EARTH_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the npc is attackable.
     *
     * @param section section for the npc to check
     * @return true if the npc is attackable, false otherwise
     */
    private boolean isAttackable(Section section) {
        String data = section.get(ATTACKABLE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section corresponds to a hostile npc.
     *
     * @param section section for the npc
     * @return true if the npc is hostile, false otherwise
     */
    private boolean isHostile(Section section) {
        String data = section.get(HOSTILE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section corresponds to a tameable npc.
     *
     * @param section section for the item to check
     * @return true if the item is tameable, false otherwise
     */
    private boolean isTameable(Section section) {
        String data = section.get(TAMEABLE_KEY);
        return data != null && !"0".equals(data);
    }


    /**
     * Checks if the npc can poison.
     *
     * @param section section for the npc
     * @return true if the npc can poison, false otherwise
     */
    private boolean canPoison(Section section) {
        String data = section.get(CAN_POISON_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section has an original position.
     *
     * @param section section for the npc
     * @return true if the npc has the original position, false otherwise
     */
    private boolean hasOriginalPosition(Section section) {
        String data = section.get(ORIGINAL_POSITION_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section is paralyzable.
     *
     * @param section section for the npc
     * @return true if the npc is paralyzable, false otherwise
     */
    private boolean isParalyzable(Section section) {
        String data = section.get(PARALYZABLE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Retrieves a drop from the given section.
     *
     * @param section section from which to load the drop
     * @return the drop to be used
     */
    private Drop getDrops(Section section) {
        String data = section.get(ITEMS_AMOUNT_KEY);
        if (data == null) return null;

        // Is it a Pretorian NPC?
        String movement = section.get(MOVEMENT_KEY);

        if (movement == null) return null;

        // Pretorian NPCs drop everything... a little bit hard coded, but we want to be compatible with old AO..
        LegacyAIType aiType = LegacyAIType.valueOf(Integer.parseInt(movement));
        if (aiType.isPretorian()) return new DropEverything(getInventory(section));

        int count = Integer.parseInt(data);

        List<Dropable> dropables = new LinkedList<>();
        String slot;

        /*
         * In Argentum, there is a 10% chance of dropping nothing; the other 90%
         * is split among the count items in such a way that each has 10% the
         * chance of the previous one.
         */
        float chance = 0.9f;
        for (int i = 1; i <= count; i++) {
            slot = section.get(DROP_PREFIX + i);
            if (slot != null) {
                String[] slotInfo = slot.split("-");
                // In every step except the last one, leave a 10% chance for the next level
                float curChance = i == count ? chance : chance * 0.9f;
                dropables.add(new Dropable(Integer.parseInt(slotInfo[0]), Integer.parseInt(slotInfo[1]), curChance));
            }

            // The chance for the next step is 10% of the current one
            chance *= 0.1f;
        }

        if (dropables.size() > 0) return new RandomDrop(dropables);

        return null;
    }

    // TODO Use this!

    /**
     * Retrieves all sounds an item may reproduce.
     *
     * @param section section from which to retrieve values
     * @return the list of sounds the item may reproduce
     */
    private List<Integer> getSounds(Section section) {
        List<Integer> sounds = new LinkedList<>();
        String data;
        for (int i = 1; i <= MAX_SOUNDS; i++) {
            data = section.get(SOUND_PREFIX + i);
            if (data != null) sounds.add(Integer.parseInt(data));
        }
        return sounds;
    }

    /**
     * Loads the NPC's inventory.
     *
     * @param section section from which to load the NPC's inventory
     * @return the NPC's inventory
     */
    private Inventory getInventory(Section section) {
        String data = section.get(ITEMS_AMOUNT_KEY);
        if (data == null) return null;

        String slot;
        byte inventorySize = Byte.parseByte(data);

        Inventory inventory = new InventoryImpl(inventorySize);

        for (byte i = 1; i <= inventorySize; i++) {
            slot = section.get(OBJECT_INVENTORY_PREFIX + i);
            if (slot != null) {
                String[] slotInfo = slot.split("-");
                int objId = Integer.parseInt(slotInfo[0]);
                int amount = Integer.parseInt(slotInfo[1]);

                WorldObjectProperties woProperties = worldObjectPropertiesDAO.getWorldObjectProperties(objId);
                WorldObject worldObject;
                try {
                    worldObject = worldObjectFactory.getWorldObject(woProperties, amount);
                } catch (WorldObjectFactoryException e) {
                    logger.error("An NPC has an item in it's inventory that can't be created. Object id: " + objId + ". Ignoring it...", e);
                    continue;
                }

                if (worldObject instanceof Item) inventory.addItem((Item) worldObject);
                else
                    logger.error("An NPC has the object with id " + objId + " in it's inventory, but the object is not an item. Ignoring it...");

            }
        }

        return inventory;
    }

    /**
     * Loads the creatures the NPC can summon.
     *
     * @param section section from which to load the creatures
     * @return A Map of the NPC's id to their names
     */
    private Map<Integer, String> getCreatures(Section section) {
        String data = section.get(CREATURES_AMOUNT_KEY);
        if (data == null) return null;
        String creatureId;
        String creatureName;
        Map<Integer, String> creatures = null;
        for (int i = 1; i <= Integer.parseInt(data); i++) {
            creatureId = section.get(CREATURE_ID_PREFIX + i);
            creatureName = section.get(CREATURE_NAME_PREFIX + i);
            if (creatureId != null && creatureName != null) {
                if (null == creatures) creatures = new HashMap<>();
                creatures.put(Integer.parseInt(creatureId), creatureName);
            }
        }
        return creatures;
    }

    /**
     * Retrieves the NPC's alignment.
     *
     * @param section section from which to load the alignment
     * @return the NPC's alignment
     */
    private Alignment getAlignment(Section section) {
        String data = section.get(ALIGNMENT_KEY);
        if (!"0".equals(data)) return Alignment.CRIMINAL;
        return Alignment.CITIZEN;
    }

    /**
     * Retrieves the types of the items to be accepted in commerce.
     *
     * @param section section from which to load the types of items accepted in commerce
     * @return the set of types of items to be accepted
     */
    private Set<WorldObjectType> getItemsType(Section section) {
        String data = section.get(ITEMS_TYPE_KEY);
        if (data == null) return null;
        Set<WorldObjectType> acceptedTypes;
        LegacyWorldObjectType objectType = LegacyWorldObjectType.valueOf(Integer.parseInt(data));
        if (objectType == null) {
            // All item types are accepted
            acceptedTypes = new HashSet<WorldObjectType>();
            acceptedTypes.addAll(Arrays.asList(WorldObjectType.values()));
        } else acceptedTypes = objectType.getPlausibleCurrentType();
        return acceptedTypes;
    }

    /**
     * Checks if the npc has an inventory respawnable.
     *
     * @param section section for the npc to check
     * @return true if the npc has an inventory respawneable, false otherwise
     */
    private boolean hasInventoryRespawn(Section section) {
        String data = section.get(INVENTORY_RESPAWN_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section corresponds has respawn.
     *
     * @param section section for the npc to check
     * @return true if the npc has respawn, false otherwise
     */
    private boolean hasRespawn(Section section) {
        String data = section.get(RESPAWNABLE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Retrieves an npc's city.
     *
     * @param section section from which to read the npc's city
     * @return the npc's city
     */
    private City getCity(Section section) {
        String data = section.get(CITY_KEY);
        if (data == null) return null;
        // TODO Implementar Cities :d
        return null;
    }

    /**
     * Checks if the section corresponds to a commerciable npc.
     *
     * @param section section for the npc to check
     * @return true if the npc is commerciable, false otherwise
     */
    private boolean isCommerciable(Section section) {
        String data = section.get(COMMERCIABLE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Retrieves an npc's description.
     *
     * @param section section from which to read the npc's description
     * @return the npc's description
     */
    private String getDescription(Section section) {
        String data = section.get(DESCRIPTION_KEY);
        if (data == null) return null;
        return data;
    }

    /**
     * Retrieves a npc's behavior from its section.
     *
     * @param section section from which to read the npc's attack strategy
     * @return the npc's behavior
     */
    private Class<? extends Behavior> getBehavior(Section section) {
        String data = section.get(MOVEMENT_KEY);
        if (data == null) return null;
        LegacyAIType aiType = LegacyAIType.valueOf(Integer.parseInt(data));
        if (null == aiType) {
            logger.warn("Invalid AIType found! Value: " + data);
            return null;
        }
        return aiType.getBehavior();
    }

    /**
     * Retrieves a npc's attack strategy from its section.
     *
     * @param section section from which to read the npc's attack strategy
     * @return the npc's attack strategy
     */
    private Class<? extends AttackStrategy> getAttackStrategy(Section section) {
        String data = section.get(MOVEMENT_KEY);
        if (data == null) return null;
        LegacyAIType aiType = LegacyAIType.valueOf(Integer.parseInt(data));
        if (null == aiType) {
            logger.warn("Invalid AIType found! Value: " + data);
            return null;
        }
        return aiType.getAttackStrategy();
    }

    /**
     * Retrieves a npc's movement strategy from its section.
     *
     * @param section section from which to read the npc's movement strategy
     * @return the npc's movement strategy
     */
    private Class<? extends MovementStrategy> getMovementStrategy(Section section) {
        String data = section.get(MOVEMENT_KEY);
        if (data == null) return null;
        LegacyAIType aiType = LegacyAIType.valueOf(Integer.parseInt(data));
        if (null == aiType) {
            logger.warn("Invalid AIType found! Value: " + data);
            return null;
        }
        return aiType.getMovementStrategy();
    }

    /**
     * NPC Type enumeration, as it was known in the old days of Visual Basic.
     */
    private enum LegacyNPCType {
        COMMON(0),
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

        private final int value;

        /**
         * Creates a new LegacyWorldObjectType.
         *
         * @param value value corresponding to the object type. Should be unique
         */
        LegacyNPCType(int value) {
            this.value = value;
        }

        /**
         * Retrieves the LegacyWorldObjectType associated with the given value.
         *
         * @param value value for which to search for a LegacyWorldObjectType
         * @return the matched LegacyWorldObjectType, if any
         */
        public static LegacyNPCType valueOf(int value) {
            for (LegacyNPCType type : LegacyNPCType.values())
                if (type.value == value) return type;
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

        private final int value;
        private final Class<? extends Behavior> behavior;
        private final Class<? extends AttackStrategy> attackStrategy;
        private final Class<? extends MovementStrategy> movementStrategy;

        /**
         * @param value            numeric value associated with the AI Type
         * @param behavior         behavior class to be used
         * @param attackStrategy   attack strategy class to be used
         * @param movementStrategy movement strategy class to be used
         */
        LegacyAIType(int value, Class<? extends Behavior> behavior,
                     Class<? extends AttackStrategy> attackStrategy,
                     Class<? extends MovementStrategy> movementStrategy) {
            this.value = value;
            this.behavior = behavior;
            this.attackStrategy = attackStrategy;
            this.movementStrategy = movementStrategy;
        }

        /**
         * Retrieves the LegacyAIType associated with the given value.
         *
         * @param value value for which to search for a LegacyAIType
         * @return the matched LegacyAIType, if any
         */
        public static LegacyAIType valueOf(int value) {
            for (LegacyAIType type : LegacyAIType.values())
                if (type.value == value) return type;
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
            return this == PRETORIAN_HUNTER || this == PRETORIAN_KING
                    || this == PRETORIAN_MAGE || this == PRETORIAN_PRIEST
                    || this == PRETORIAN_WARRIOR;
        }

    }

}
