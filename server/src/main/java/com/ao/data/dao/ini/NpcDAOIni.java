package com.ao.data.dao.ini;

import com.ao.data.dao.NpcCharacterDAO;
import com.ao.data.dao.ObjectDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.AIType;
import com.ao.model.character.Alignment;
import com.ao.model.character.NpcType;
import com.ao.model.character.attack.AttackStrategy;
import com.ao.model.character.behavior.Behavior;
import com.ao.model.character.movement.MovementStrategy;
import com.ao.model.character.npc.Drop;
import com.ao.model.character.npc.drop.DropEverything;
import com.ao.model.character.npc.drop.Dropable;
import com.ao.model.character.npc.drop.RandomDrop;
import com.ao.model.character.npc.properties.*;
import com.ao.model.inventory.Inventory;
import com.ao.model.inventory.InventoryImpl;
import com.ao.model.map.City;
import com.ao.model.map.Heading;
import com.ao.model.object.Item;
import com.ao.model.object.ObjectType;
import com.ao.model.object.factory.ObjectFactory;
import com.ao.model.object.factory.ObjectFactoryException;
import com.ao.model.object.properties.ObjectProperties;
import com.ao.model.spell.Spell;
import com.ao.service.MapService;
import com.ao.utils.IniUtils;
import com.ao.utils.ResourceUtils;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.tinylog.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Implementation of the npc DAO backed by INI files.
 * <p>
 * Most flags in {@code npcs.dat} like <b>poisonous</b>, accept values 0 (false) and 1 (true). Some flags like <b>tameable</b>
 * accept other values.
 * <p>
 * Numeric keys with value 0 needn't have value 0. Missing keys default to 0. For example, if the Duende (section [NPC196]) has no
 * defense, then it is redundant to specify {@code defense = 0} in the file.
 * <p>
 * The sentinel value for keys not found or invalid values is {@code -1}.
 * <p>
 * TODO Creo que no se esta inyectando WorldObjectFactory
 */

public final class NpcDAOIni implements NpcCharacterDAO {

    private static final String INIT_HEADER = "INIT";
    private static final String NPC_COUNT_KEY = "npc_count";

    // TODO Podria mover la declaracion de las keys en una clase aparte

    /** Prefixs keys. */
    private static final String NPC_PREFIX = "NPC";
    private static final String DROP_PREFIX = "drop";
    private static final String OBJECT_INVENTORY_PREFIX = "object"; // obj
    private static final String SPELL_PREFIX = "spell"; // Sp
    private static final String SOUND_PREFIX = "sound"; // SND
    private static final String CREATURE_ID_PREFIX = "creature_id"; // CI
    private static final String CREATURE_NAME_PREFIX = "creature_name"; // CN

    /** Ini file keys. */
    private static final String NAME_KEY = "name";
    private static final String NPC_TYPE_KEY = "npc_type"; // NpcType
    private static final String DESCRIPTION_KEY = "description";
    private static final String HEAD_KEY = "head";
    private static final String HEADING_KEY = "heading";
    private static final String BODY_KEY = "body";
    private static final String AI_TYPE_KEY = "ai_type"; // Movement
    private static final String RETURNING_KEY = "returning"; // PosOrig
    private static final String CITY_KEY = "city";
    private static final String CREATURE_COUNT_KEY = "creature_count"; // NroCriaturas
    private static final String ALIGNMENT_KEY = "alignment";
    private static final String AQUATIC_KEY = "aquatic"; // AguaValida
    private static final String MERCHANT_KEY = "merchant"; // Comercia
    private static final String ATTACKABLE_KEY = "attackable";
    private static final String HOSTILE_KEY = "hostile";
    private static final String POISONOUS_KEY = "poisonous"; // Veneno
    private static final String UNPARALYZABLE_KEY = "unparalyzable"; // AfectaParalisis
    private static final String RESPAWNABLE_KEY = "respawnable"; // ReSpawn
    private static final String TAMEABLE_KEY = "temeable"; // Domable
    private static final String EXPERIENCE_KEY = "experience"; // GiveEXP
    private static final String GOLD_KEY = "gold"; // GiveGLD
    private static final String MIN_HP_KEY = "min_hp";
    private static final String MAX_HP_KEY = "max_hp";
    private static final String MAX_HIT_KEY = "max_hit";
    private static final String MIN_HIT_KEY = "min_hit";
    private static final String DEFENSE_KEY = "defense"; // Def
    private static final String MAGIC_DEFENSE_KEY = "magic_defense"; // DefM
    private static final String ATTACK_KEY = "attack";
    private static final String EVASION_KEY = "evasion"; // PoderEvasion
    private static final String SPELL_COUNT_KEY = "spell_count"; // LanzaSpells
    private static final String OBJECT_TYPE_KEY = "object_type"; // TipoItems
    private static final String OBJECT_COUNT_KEY = "object_count"; // NROITEMS
    private static final String RESTOCKABLE_KEY = "restockable"; // InvReSpawn

    private final String npcsFilePath;
    private final ObjectDAO objectDAO;
    private final ObjectFactory objectFactory;
    private final MapService mapService;

    /**
     * Creates a new NpcDAOIni instance using DI.
     * <p>
     * In the module {@code DaoModule} we already have {@code bind(WorldObjectPropertiesDAO.class).to(...)}. For
     * {@code WorldObjectFactory} a similar binding must exist in another module; if it didn't exist, Guice would fail when
     * creating the Injector.
     *
     * @param npcsFilePath  path to the file with all objects definitions
     * @param objectDAO     DAO for World Object Properties
     * @param objectFactory factory to create world object instances
     * @param mapService    map service
     */
    @Inject
    public NpcDAOIni(@Named("npcsFilePath") String npcsFilePath, ObjectDAO objectDAO, ObjectFactory objectFactory, MapService mapService) {
        this.npcsFilePath = npcsFilePath;
        this.objectDAO = objectDAO;
        this.objectFactory = objectFactory;
        this.mapService = mapService;
    }

    @Override
    public Npc[] load() throws DAOException {
        INIConfiguration ini;
        InputStream inputStream = ResourceUtils.getStream(npcsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + npcsFilePath + "' was not found!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8))) {
            ini = new INIConfiguration();
            ini.read(reader);
            Logger.info("Npcs loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            throw new DAOException("Error loading npcs!\n" + e);
        }

        // Required key
        int npcCount = IniUtils.getRequiredInt(ini, INIT_HEADER + "." + NPC_COUNT_KEY);

        Npc[] npcs = new Npc[npcCount];

        for (int i = 1; i <= npcCount; i++)
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
    private Npc loadNpc(int id, INIConfiguration ini) {

        String sectionName = NPC_PREFIX + id;
        SubnodeConfiguration section = ini.getSection(sectionName);

        String name = IniUtils.getString(section, NAME_KEY, "");
        String description = IniUtils.getString(section, DESCRIPTION_KEY, "");
        short head = (short) IniUtils.getInt(section, HEAD_KEY, 0);
        Heading heading = Heading.get((byte) (IniUtils.getInt(section, HEADING_KEY, 1) - 1));
        short body = (short) IniUtils.getInt(section, BODY_KEY, 0);
        boolean respawnable = IniUtils.getBoolean(section, RESPAWNABLE_KEY, true);
        AIType aiType = getAIType(section, sectionName);
        Class<? extends Behavior> behavior = aiType != null ? aiType.getBehavior() : null;
        Class<? extends AttackStrategy> attackStrategy = aiType != null ? aiType.getAttackStrategy() : null;
        Class<? extends MovementStrategy> movementStrategy = aiType != null ? aiType.getMovementStrategy() : null;

        NpcType npcType = NpcType.findById(IniUtils.getInt(section, NPC_TYPE_KEY, -1));

        if (npcType == null) {
            Logger.error("Unknown npc type in section [{}]", sectionName);
            return null;
        }

        return switch (npcType) {
            case COMMON ->
                    loadCreature(NpcType.COMMON, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, aiType, section, sectionName);
            case DRAGON, PRETORIAN ->
                    loadCreature(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, aiType, section, sectionName);
            case TRAINER ->
                    loadTrainer(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, section, sectionName);
            case GOVERNOR ->
                    loadGovernor(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, section, sectionName);
            case ROYAL_GUARD, CHAOS_GUARD ->
                    loadGuard(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, aiType, section, sectionName);
            case NOBLE ->
                    loadNoble(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, section);
            case NEWBIE_RESUCITATOR, RESUCITATOR, GAMBLER, BANKER ->
                    loadBasicNpc(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy);
            case MERCHANT ->
                    loadMerchant(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, section, sectionName);
        };

    }

    private Npc loadNoble(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                          String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                          Class<? extends MovementStrategy> movementStrategy, SubnodeConfiguration section) {

        return new NobleNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getAlignment(section));
    }

    private Npc loadBasicNpc(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy) {

        return new Npc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy);
    }

    private Npc loadGovernor(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, SubnodeConfiguration section, String sectionName) {

        return new GovernorNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getCity(section, sectionName));
    }

    private Npc loadMerchant(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, SubnodeConfiguration section, String sectionName) {

        return new MerchantNpc(type, id, name, body, head, heading, respawnable, behavior, attackStrategy, movementStrategy,
                description, getInventory(section), isRestockable(section), getAcceptedObjectTypes(section, sectionName));
    }

    private Npc loadTrainer(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                            String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                            Class<? extends MovementStrategy> movementStrategy, SubnodeConfiguration section, String sectionName) {

        return new TrainerNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getCreatures(section, sectionName));
    }

    // TODO Separar metodo para npc hostiles y varios/decorativos o cambiar nombre de CreatureNpc a algun nombre mas generico
    private Npc loadCreature(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, AIType aiType, SubnodeConfiguration section, String sectionName) {

        return new CreatureNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy,
                getExperience(section), getGold(section), getMinHP(section), getMaxHP(section),
                getMinHit(section), getMaxHit(section), getDefense(section), getMagicDefense(section), getAttack(section),
                getEvasion(section), getSpells(section, sectionName), isAquatic(section), isAttackable(section),
                isPoisonous(section), isUnparalyzable(section), isHostile(section), isTameable(section), getDrops(aiType, section, sectionName));
    }

    private Npc loadGuard(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                          String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                          Class<? extends MovementStrategy> movementStrategy, AIType aiType, SubnodeConfiguration section, String sectionName) {

        return new GuardNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy,
                getExperience(section), getGold(section), getMinHP(section), getMaxHP(section), getMinHit(section),
                getMaxHit(section), getDefense(section), getMagicDefense(section), getAttack(section), getEvasion(section),
                getSpells(section, sectionName), isAquatic(section), isAttackable(section), isPoisonous(section),
                isUnparalyzable(section), isHostile(section), isTameable(section), getDrops(aiType, section, sectionName), isReturning(section));
    }

    /**
     * Gets npc's spells.
     * <p>
     * TODO Implement
     *
     * @return the npc's spells; null if none exists
     */
    private List<Spell> getSpells(SubnodeConfiguration section, String sectionName) {
        int spellCount = IniUtils.getInt(section, SPELL_COUNT_KEY, 0);
        if (spellCount <= 0) {
            Logger.info("No spells found for npc [{}]", sectionName);
            return null;
        }

        Logger.warn("Spell loading not implemented for section [{}]: {} spell(s) ignored", sectionName, spellCount);

        // TODO Implement
//        List<Spell> spells = null;
//        for (int i = 1; i <= spellCount; i++) {
//            String spellKey = SPELL_PREFIX + i;
//            if (!section.containsKey(spellKey)) continue;
//            int value = IniUtils.getInt(section, spellKey, -1);
//            // if (value != -1) spells.add(new Spell(value, ));
//        }
//        return spells;

        return null;
    }

    /**
     * Gets npc's drops.
     *
     * @return the npc's drops; null if none exists
     */
    private Drop getDrops(AIType aiType, SubnodeConfiguration section, String sectionName) {
        int objectCount = IniUtils.getInt(section, OBJECT_COUNT_KEY, -1);
        if (objectCount == -1) return null;

        if (aiType == null) {
            logKeyNotFoundOrInvalid(AI_TYPE_KEY, sectionName);
            return null;
        }

        // Pretorian Npcs drop all their items when killed, a little bit hardcoded, but want to be compatible with old AO
        if (aiType.isPretorian()) return new DropEverything(getInventory(section));

        List<Dropable> dropables = new ArrayList<>();

        /* In Argentum Online, a 10% chance exists for dropping nothing. The remaining 90% splits among the counted objects so
         * that each has 10% the chance of the previous one. In other words, the first object is more likely than the second. */
        float chance = 0.9f; // 90% for the first object, 10% for the second, and 10% for the third, and so
        for (int i = 1; i <= objectCount; i++) {
            /* El slot contiene el id y la cantidad del objeto dropeable, por ejemplo para el objeto [NPC159] la clave drop1
             * contiene el valor "12-8", en donde el 12 es el id del objeto a dropear y el 8 la cantidad, y el simbolo '-' es un
             * separador. En este caso, el id 12 serian monedas de oro ([OBJ12] en objects.dat). El ultimo drop (drop5),
             * representa el objeto [OBJ197] y es el que menos probabilidades tiene de dropear. */
            String slot = IniUtils.getString(section, DROP_PREFIX + i, "");
            if (!slot.isEmpty()) {
                String[] slotInfo = slot.split("-");
                // In every step except the last one, leave a 10% chance for the next level
                float currentChance = i == objectCount ? chance : chance * 0.9f;
                try {
                    dropables.add(new Dropable(Integer.parseInt(slotInfo[0]), Integer.parseInt(slotInfo[1]), currentChance));
                } catch (NumberFormatException e) {
                    Logger.warn("Malformed drop slot '{}' in section [{}], skipping", slot, sectionName);
                }
            }

            // The chance for the next step is 10% of the current one
            chance *= 0.1f;
        }

        // De todos los posibles objetos dropeables que un npc tiene, solo suelta uno
        if (!dropables.isEmpty()) return new RandomDrop(dropables);

        return null;
    }

    /**
     * Gets the inventory from the npc.
     *
     * @return the inventory from the npc; null if none exists
     */
    private Inventory getInventory(SubnodeConfiguration section) {
        int objectCount = IniUtils.getInt(section, OBJECT_COUNT_KEY, -1);
        if (objectCount == -1) return null;

        Inventory inventory = new InventoryImpl(objectCount);

        for (int i = 1; i <= inventory.getCapacity(); i++) {
            String slot = IniUtils.getString(section, OBJECT_INVENTORY_PREFIX + i, "");
            if (!slot.isEmpty()) {
                String[] slotInfo = slot.split("-");
                int objId;
                int amount;
                try {
                    objId = Integer.parseInt(slotInfo[0]);
                    amount = Integer.parseInt(slotInfo[1]);
                } catch (NumberFormatException e) {
                    Logger.warn("Malformed inventory slot '{}', skipping", slot);
                    continue;
                }
                ObjectProperties objectProperties = objectDAO.getObjectProperties(objId);
                Item item;
                try {
                    item = objectFactory.getObject(objectProperties, amount);
                    if (item != null) inventory.addItem(item);
                    else Logger.error("Npc has object id '{}' in inventory but it's not an item", objId);
                } catch (ObjectFactoryException e) {
                    Logger.warn("Npc with item id '{}' cannot be created", objId);
                }
            }
        }

        return inventory;
    }

    /**
     * Get the sounds from the npc.
     *
     * @return the list of sounds from the npc or empty list if none exists
     */
    private List<Integer> getSounds(SubnodeConfiguration section) {
        List<Integer> sounds = new ArrayList<>(3);
        for (int i = 1; i <= 3; i++) {
            int value = IniUtils.getInt(section, SOUND_PREFIX + i, -1);
            if (value != -1) sounds.add(value);
        }
        return sounds;
    }

    /**
     * Loads the creatures the npc can summon.
     *
     * @return the creatures the npc can summon; null if none exists
     */
    private Map<Integer, String> getCreatures(SubnodeConfiguration section, String sectionName) {
        int creatureCount = IniUtils.getInt(section, CREATURE_COUNT_KEY, -1);
        if (creatureCount == -1) {
            logKeyNotFoundOrInvalid(CREATURE_COUNT_KEY, sectionName);
            return null;
        }
        Map<Integer, String> creatures = new HashMap<>(creatureCount);
        for (int i = 1; i <= creatureCount; i++) {
            int creatureId = IniUtils.getInt(section, CREATURE_ID_PREFIX + i, -1);
            String creatureName = IniUtils.getString(section, CREATURE_NAME_PREFIX + i, "");
            if (creatureId != -1 && !creatureName.isEmpty()) creatures.put(creatureId, creatureName);
        }
        return creatures.isEmpty() ? null : creatures;
    }

    /**
     * Gets npc's alignment.
     *
     * @return the npc's alignment; or null if the key {@code ALIGNMENT_KEY} is missing or has an invalid value
     */
    private Alignment getAlignment(SubnodeConfiguration section) {
        int alignment = IniUtils.getInt(section, ALIGNMENT_KEY, 0);
        if (alignment == 0) return null; // El 0 se considera una alineacion neutral // TODO Deberia creear la constante NEUTRAL?
        return alignment == 1 ? Alignment.CITIZEN : Alignment.CRIMINAL;
    }

    /**
     * Gets the types of the object to be accepted in commerce.
     *
     * @return the types of the object to be accepted in commerce
     */
    private Set<ObjectType> getAcceptedObjectTypes(SubnodeConfiguration section, String sectionName) {
        int objectTypeId = IniUtils.getInt(section, OBJECT_TYPE_KEY, -1);
        if (objectTypeId == -1) {
            logKeyNotFoundOrInvalid(OBJECT_TYPE_KEY, sectionName);
            return null;
        }
        LegacyObjectType objectType = LegacyObjectType.findById(objectTypeId);
        return objectType == null ? EnumSet.allOf(ObjectType.class) : objectType.getMappedTypes();
    }

    /**
     * Gets npc's city.
     *
     * @return the npc's city; null if the key {@code CITY_KEY} is missing or has an invalid value, or if the city doesn't exist
     */
    private City getCity(SubnodeConfiguration section, String sectionName) {
        int cityId = IniUtils.getInt(section, CITY_KEY, -1);
        if (cityId == -1) {
            logKeyNotFoundOrInvalid(CITY_KEY, sectionName);
            return null;
        }
        if (cityId < 0 || cityId > 255) {
            Logger.warn("City id '{}' out of range [0..255] in section [{}]", cityId, sectionName);
            return null;
        }
        City city = mapService.getCity((byte) cityId);
        if (city == null) {
            Logger.warn("The city id {} does not exist!", cityId);
            return null;
        }
        // Logger.debug("[CITY{}]: map={}, x={}, y={}", cityId, city.map(), city.x(), city.y());
        return city;
    }

    /**
     * Gets AI type.
     *
     * @return the AI type; null if the key {@code AI_TYPE_KEY} is missing or has an invalid value, or if the AI type doesn't
     * exist
     */
    private AIType getAIType(SubnodeConfiguration section, String sectionName) {
        int aiTypeId = IniUtils.getInt(section, AI_TYPE_KEY, -1);
        if (aiTypeId == -1) {
            logKeyNotFoundOrInvalid(AI_TYPE_KEY, sectionName);
            return null;
        }
        AIType aiType = AIType.findById(aiTypeId);
        if (aiType == null) {
            Logger.warn("The AI type id {} does not exist!", aiTypeId);
            return null;
        }
        return aiType;
    }

    /**
     * Gets npc's experience.
     *
     * @return the npc's experience, or 0 if the key {@code EXPERIENCE_KEY} is missing or has an invalid value
     */
    private int getExperience(SubnodeConfiguration section) {
        return IniUtils.getInt(section, EXPERIENCE_KEY, 0);
    }

    /**
     * Gets npc's gold.
     *
     * @return the npc's gold, or 0 if the key {@code GOLD_KEY} is missing or has an invalid value
     */
    private int getGold(SubnodeConfiguration section) {
        return IniUtils.getInt(section, GOLD_KEY, 0);
    }

    /**
     * Gets npc's min hp.
     *
     * @return the npc's a min hp, or 0 if the key {@code MIN_HP_KEY} is missing or has an invalid value
     */
    private int getMinHP(SubnodeConfiguration section) {
        return IniUtils.getInt(section, MIN_HP_KEY, 0);
    }

    /**
     * Gets npc's max hp.
     *
     * @return the npc's max hp, or 0 if the key {@code MAX_HP_KEY} is missing or has an invalid value
     */
    private int getMaxHP(SubnodeConfiguration section) {
        return IniUtils.getInt(section, MAX_HP_KEY, 0);
    }

    /**
     * Gets npc's min hit.
     *
     * @return the npc's min hit, or 0 if the key {@code MIN_HIT_KEY} is missing or has an invalid value
     */
    private int getMinHit(SubnodeConfiguration section) {
        return IniUtils.getInt(section, MIN_HIT_KEY, 0);
    }

    /**
     * Gets npc's max hit.
     *
     * @return the npc's max hit, or 0 if the key {@code MAX_HIT_KEY} is missing or has an invalid value
     */
    private int getMaxHit(SubnodeConfiguration section) {
        return IniUtils.getInt(section, MAX_HIT_KEY, 0);
    }

    /**
     * Gets npc's attack.
     *
     * @return the npc's attack, or 0 if the key {@code ATTACK_KEY} is missing or has an invalid value
     */
    private short getAttack(SubnodeConfiguration section) {
        return (short) IniUtils.getInt(section, ATTACK_KEY, 0);
    }

    /**
     * Gets npc's defense.
     *
     * @return the npc's defense, or 0 if the key {@code DEFENSE_KEY} is missing or has an invalid value
     */
    private short getDefense(SubnodeConfiguration section) {
        // TODO Es la mejor practica hacer un cast a short?
        return (short) IniUtils.getInt(section, DEFENSE_KEY, 0);
    }

    /**
     * Gets npc's magic defense.
     *
     * @return the npc's magic defense, or 0 if the key {@code MAGIC_DEFENSE_KEY} is missing or has an invalid value
     */
    private short getMagicDefense(SubnodeConfiguration section) {
        return (short) IniUtils.getInt(section, MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets npc's evasion.
     *
     * @return the npc's evasion, or 0 if the key {@code EVASION_KEY} is missing or has an invalid value
     */
    private short getEvasion(SubnodeConfiguration section) {
        return (short) IniUtils.getInt(section, EVASION_KEY, 0);
    }

    /**
     * Check if the npc returns to its original position.
     *
     * @return true if the npc returns to its original position, or false if the key {@code RETURNING_KEY} is missing or has an
     * invalid value
     */
    private boolean isReturning(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, RETURNING_KEY, false);
    }

    /**
     * Checks if the npc has restockable keys.
     *
     * @return true if the npc has restockable keys, or false if the key {@code RESTOCKABLE_KEY} is missing or has an invalid
     * value
     */
    private boolean isRestockable(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, RESTOCKABLE_KEY, false);
    }

    /**
     * Checks if the npc is merchant.
     *
     * @return true if the npc is merchant, or false if the key {@code MERCHANT_KEY} is missing or has an invalid value
     */
    private boolean isMerchant(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, MERCHANT_KEY, false);
    }

    /**
     * Checks if the npc is aquatic.
     *
     * @return true if the npc is aquatic, or false if the key {@code AQUATIC_KEY} is missing or has an invalid value
     */
    private boolean isAquatic(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, AQUATIC_KEY, false);
    }

    /**
     * Checks if the npc is attackable.
     *
     * @return true if the npc is attackable, or false if the key {@code ATTACKABLE_KEY} is missing or has an invalid value
     */
    private boolean isAttackable(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, ATTACKABLE_KEY, false);
    }

    /**
     * Checks if the npc is hostile.
     *
     * @return true if the npc is hostile, or false if the key {@code HOSTILE_KEY} is missing or has an invalid value
     */
    private boolean isHostile(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, HOSTILE_KEY, false);
    }

    /**
     * Checks if the npc is tameable.
     *
     * @return true if the npc is tameable, or false if the key {@code TAMEABLE_KEY} is missing or has an invalid value
     */
    private boolean isTameable(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, TAMEABLE_KEY, false);
    }

    /**
     * Checks if the npc is poisonous.
     *
     * @return true if the npc is poisonous, or false if the key {@code POISONOUS_KEY} is missing or has an invalid value
     */
    private boolean isPoisonous(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, POISONOUS_KEY, false);
    }

    /**
     * Checks if the npc is unparalyzable.
     *
     * @return true if the npc is unparalyzable, or false if the key {@code UNPARALYZABLE_KEY} is missing or has an invalid value
     */
    private boolean isUnparalyzable(SubnodeConfiguration section) {
        return IniUtils.getBoolean(section, UNPARALYZABLE_KEY, false);
    }

    private void logKeyNotFoundOrInvalid(String key, String sectionName) {
        Logger.warn("The key '{}' was not found in section [{}] or its value is invalid!", key, sectionName);
    }

}
