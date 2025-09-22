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
import com.ao.model.spell.Spell;
import com.ao.model.worldobject.Item;
import com.ao.model.worldobject.ObjectType;
import com.ao.model.worldobject.factory.ObjectFactory;
import com.ao.model.worldobject.factory.ObjectFactoryException;
import com.ao.model.worldobject.properties.ObjectProperties;
import com.ao.service.MapService;
import com.ao.utils.IniUtils;
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
import java.util.stream.IntStream;

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

public record NpcDAOIni(String npcsFilePath,
                        ObjectDAO objectDAO,
                        ObjectFactory objectFactory,
                        MapService mapService) implements NpcCharacterDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NpcDAOIni.class);

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
        INIConfiguration ini = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(npcsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + npcsFilePath + "' was not found in the classpath!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
            LOGGER.info("Npcs loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading npcs!", e);
            System.exit(-1);
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

        String section = NPC_PREFIX + id;

        String name = IniUtils.getString(ini, section + "." + NAME_KEY, "");
        String description = IniUtils.getString(ini, section + "." + DESCRIPTION_KEY, "");
        short head = (short) IniUtils.getInt(ini, section + "." + HEAD_KEY, 0);
        Heading heading = Heading.get((byte) (IniUtils.getInt(ini, section + "." + HEADING_KEY, 1) - 1));
        short body = (short) IniUtils.getInt(ini, section + "." + BODY_KEY, 0);
        boolean respawnable = IniUtils.getBoolean(ini, section + "." + RESPAWNABLE_KEY, true);
        Class<? extends Behavior> behavior = getBehavior(ini, section);
        Class<? extends AttackStrategy> attackStrategy = getAttackStrategy(ini, section);
        Class<? extends MovementStrategy> movementStrategy = getMovementStrategy(ini, section);

        NpcType npcType = NpcType.findById(IniUtils.getInt(ini, section + "." + NPC_TYPE_KEY, -1));

        if (npcType == null) {
            LOGGER.error("Unknown npc type in section [{}]", section);
            return null;
        }

        return switch (npcType) {
            case COMMON ->
                    loadCreature(NpcType.COMMON, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case DRAGON, PRETORIAN ->
                    loadCreature(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case TRAINER ->
                    loadTrainer(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case GOVERNOR ->
                    loadGovernor(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case ROYAL_GUARD, CHAOS_GUARD ->
                    loadGuard(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case NOBLE ->
                    loadNoble(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
            case NEWBIE_RESUCITATOR, RESUCITATOR, GAMBLER, BANKER ->
                    loadBasicNpc(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy);
            case MERCHANT ->
                    loadMerchant(npcType, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, ini, section);
        };

    }

    private Npc loadNoble(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                          String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                          Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new NobleNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getAlignment(ini, section));
    }

    private Npc loadBasicNpc(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy) {

        return new Npc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy);
    }

    private Npc loadGovernor(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new GovernorNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getCity(ini, section));
    }

    private Npc loadMerchant(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new MerchantNpc(type, id, name, body, head, heading, respawnable, behavior, attackStrategy, movementStrategy,
                description, getInventory(ini, section), isRestockable(ini, section), getAcceptedObjectTypes(ini, section));
    }

    private Npc loadTrainer(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                            String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                            Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new TrainerNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy, getCreatures(ini, section));
    }

    // TODO Separar metodo para npc hostiles y varios/decorativos o cambiar nombre de CreatureNpc a algun nombre mas generico
    private Npc loadCreature(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                             String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                             Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new CreatureNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy,
                getExperience(ini, section), getGold(ini, section), getMinHP(ini, section), getMaxHP(ini, section),
                getMinHit(ini, section), getMaxHit(ini, section), getDefense(ini, section), getMagicDefense(ini, section), getAttack(ini, section),
                getEvasion(ini, section), getSpells(ini, section), isAquatic(ini, section), isAttackable(ini, section),
                isPoisonous(ini, section), isUnparalyzable(ini, section), isHostile(ini, section), isTameable(ini, section), getDrops(ini, section));
    }

    private Npc loadGuard(NpcType type, int id, String name, short body, short head, Heading heading, boolean respawnable,
                          String description, Class<? extends Behavior> behavior, Class<? extends AttackStrategy> attackStrategy,
                          Class<? extends MovementStrategy> movementStrategy, INIConfiguration ini, String section) {

        return new GuardNpc(type, id, name, body, head, heading, respawnable, description, behavior, attackStrategy, movementStrategy,
                getExperience(ini, section), getGold(ini, section), getMinHP(ini, section), getMaxHP(ini, section), getMinHit(ini, section),
                getMaxHit(ini, section), getDefense(ini, section), getMagicDefense(ini, section), getAttack(ini, section), getEvasion(ini, section),
                getSpells(ini, section), isAquatic(ini, section), isAttackable(ini, section), isPoisonous(ini, section),
                isUnparalyzable(ini, section), isHostile(ini, section), isTameable(ini, section), getDrops(ini, section), isReturning(ini, section));
    }

    /**
     * Gets npc's spells.
     * <p>
     * TODO Implement
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's spells; null if none exists
     */
    private List<Spell> getSpells(INIConfiguration ini, String section) {
        int spellCount = IniUtils.getInt(ini, section + "." + SPELL_COUNT_KEY, 0);
        if (spellCount <= 0) {
            LOGGER.warn("No spells found for npc [{}]", section);
            return null;
        }

        List<Spell> spells = null;
        for (int i = 1; i <= spellCount; i++) {
            String spell = section + "." + SPELL_PREFIX + i;
            if (!ini.containsKey(spell)) continue;
            int value = IniUtils.getInt(ini, spell, -1);
            // if (value != -1) spells.add(new Spell(value, ));
        }
        return spells;
    }

    /**
     * Gets npc's drops.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's drops; null if none exists
     */
    private Drop getDrops(INIConfiguration ini, String section) {
        int objectCount = IniUtils.getInt(ini, section + "." + OBJECT_COUNT_KEY, -1);
        if (objectCount == -1) return null;

        int aiTypeId = IniUtils.getInt(ini, section + "." + AI_TYPE_KEY, -1);

        if (aiTypeId == -1) {
            logKeyNotFoundOrInvalid(AI_TYPE_KEY, section);
            return null;
        }

        AIType aiType = AIType.findById(aiTypeId);
        if (aiType == null) {
            LOGGER.warn("The AI type id {} does not exist!", aiTypeId);
            return null;
        }

        // Pretorian Npcs drop all their items when killed, a little bit hardcoded, but want to be compatible with old AO
        if (aiType.isPretorian()) return new DropEverything(getInventory(ini, section));

        List<Dropable> dropables = new LinkedList<>();

        /* In Argentum Online, a 10% chance exists for dropping nothing. The remaining 90% splits among the counted objects so
         * that each has 10% the chance of the previous one. In other words, the first object is more likely than the second. */
        float chance = 0.9f; // 90% for the first object, 10% for the second, and 10% for the third, and so
        for (int i = 1; i <= objectCount; i++) {
            /* El slot contiene el id y la cantidad del objeto dropeable, por ejemplo para el objeto [NPC159] la clave drop1
             * contiene el valor "12-8", en donde el 12 es el id del objeto a dropear y el 8 la cantidad, y el simbolo '-' es un
             * separador. En este caso, el id 12 serian monedas de oro ([OBJ12] en objects.dat). El ultimo drop (drop5),
             * representa el objeto [OBJ197] y es el que menos probabilidades tiene de dropear. */
            String slot = IniUtils.getString(ini, section + "." + DROP_PREFIX + i, "");
            if (!slot.isEmpty()) {
                String[] slotInfo = slot.split("-");
                // In every step except the last one, leave a 10% chance for the next level
                float currentChance = i == objectCount ? chance : chance * 0.9f;
                dropables.add(new Dropable(Integer.parseInt(slotInfo[0]), Integer.parseInt(slotInfo[1]), currentChance));
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
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the inventory from the npc; null if none exists
     */
    private Inventory getInventory(INIConfiguration ini, String section) {
        int objectCount = IniUtils.getInt(ini, section + "." + OBJECT_COUNT_KEY, -1);
        if (objectCount == -1) return null;

        Inventory inventory = new InventoryImpl(objectCount);

        for (int i = 1; i <= inventory.getCapacity(); i++) {
            String slot = IniUtils.getString(ini, section + "." + OBJECT_INVENTORY_PREFIX + i, "");
            if (!slot.isEmpty()) {
                String[] slotInfo = slot.split("-");
                int objId = Integer.parseInt(slotInfo[0]);
                int amount = Integer.parseInt(slotInfo[1]);
                ObjectProperties objectProperties = objectDAO.getObjectProperties(objId);
                Item item;
                try {
                    item = objectFactory.getObject(objectProperties, amount);
                    if (item != null) inventory.addItem(item);
                    else LOGGER.error("Npc has object id '{}' in inventory but it's not an item", objId);
                } catch (ObjectFactoryException e) {
                    LOGGER.warn("Npc with item id '{}' cannot be created", objId);
                }
            }
        }

        return inventory;
    }

    /**
     * Get the sounds from the npc.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the list of sounds from the npc or empty list if none exists
     */
    private List<Integer> getSounds(INIConfiguration ini, String section) {
        int soundCount = 3;
        return IntStream.rangeClosed(1, soundCount)
                .map(i -> IniUtils.getInt(ini, section + "." + SOUND_PREFIX + i, -1))
                .filter(value -> value != -1)
                .boxed()
                .toList();
    }

    /**
     * Loads the creatures the npc can summon.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the creatures the npc can summon; null if none exists
     */
    private Map<Integer, String> getCreatures(INIConfiguration ini, String section) {
        int creatureCount = IniUtils.getInt(ini, section + "." + CREATURE_COUNT_KEY, -1);
        if (creatureCount == -1) {
            logKeyNotFoundOrInvalid(CREATURE_COUNT_KEY, section);
            return null;
        }
        Map<Integer, String> creatures = new HashMap<>(creatureCount);
        for (int i = 1; i <= creatureCount; i++) {
            int creatureId = IniUtils.getInt(ini, section + "." + CREATURE_ID_PREFIX + i, -1);
            String creatureName = IniUtils.getString(ini, section + "." + CREATURE_NAME_PREFIX + i, "");
            if (creatureId != -1 && !creatureName.isEmpty()) creatures.put(creatureId, creatureName);
        }
        return creatures.isEmpty() ? null : creatures;
    }

    /**
     * Gets npc's alignment.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's alignment; or null if the key {@code ALIGNMENT_KEY} is missing or has an invalid value
     */
    private Alignment getAlignment(INIConfiguration ini, String section) {
        int alignment = IniUtils.getInt(ini, section + "." + ALIGNMENT_KEY, 0);
        if (alignment == 0) return null; // El 0 se considera una alineacion neutral // TODO Deberia creear la constante NEUTRAL?
        return alignment == 1 ? Alignment.CITIZEN : Alignment.CRIMINAL;
    }

    /**
     * Gets the types of the object to be accepted in commerce.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the types of the object to be accepted in commerce
     */
    private Set<ObjectType> getAcceptedObjectTypes(INIConfiguration ini, String section) {
        int objectTypeId = IniUtils.getInt(ini, section + "." + OBJECT_TYPE_KEY, -1);
        if (objectTypeId == -1) {
            logKeyNotFoundOrInvalid(OBJECT_TYPE_KEY, section);
            return null;
        }
        LegacyObjectType objectType = LegacyObjectType.findById(objectTypeId);
        return objectType == null ? EnumSet.allOf(ObjectType.class) : objectType.getMappedTypes();
    }

    /**
     * Gets npc's city.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's city; null if the key {@code CITY_KEY} is missing or has an invalid value, or if the city doesn't exist
     */
    private City getCity(INIConfiguration ini, String section) {
        int cityId = IniUtils.getInt(ini, section + "." + CITY_KEY, -1);
        if (cityId == -1) {
            logKeyNotFoundOrInvalid(CITY_KEY, section);
            return null;
        }
        if (cityId < 0 || cityId > 255) {
            LOGGER.warn("City id '{}' out of range [0..255] in section [{}]", cityId, section);
            return null;
        }
        City city = mapService.getCity((byte) cityId);
        if (city == null) {
            LOGGER.warn("The city id {} does not exist!", cityId);
            return null;
        }
        // LOGGER.debug("[CITY{}]: map={}, x={}, y={}", cityId, city.map(), city.x(), city.y());
        return city;
    }

    /**
     * Gets npc behavior.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc behavior or null if the AIType is null
     */
    private Class<? extends Behavior> getBehavior(INIConfiguration ini, String section) {
        AIType aiType = getAIType(ini, section);
        return aiType != null ? aiType.getBehavior() : null;
    }

    /**
     * Gets npc attack strategy.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc attack strategy or null if the AIType is null
     */
    private Class<? extends AttackStrategy> getAttackStrategy(INIConfiguration ini, String section) {
        AIType aiType = getAIType(ini, section);
        return aiType != null ? aiType.getAttackStrategy() : null;
    }

    /**
     * Gets npc movement strategy.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc movement strategy or null if the AIType is null
     */
    private Class<? extends MovementStrategy> getMovementStrategy(INIConfiguration ini, String section) {
        AIType aiType = getAIType(ini, section);
        return aiType != null ? aiType.getMovementStrategy() : null;
    }

    /**
     * Gets AI type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the AI type; null if the key {@code AI_TYPE_KEY} is missing or has an invalid value, or if the AI type doesn't
     * exist
     */
    private AIType getAIType(INIConfiguration ini, String section) {
        int aiTypeId = IniUtils.getInt(ini, section + "." + AI_TYPE_KEY, -1);
        if (aiTypeId == -1) {
            logKeyNotFoundOrInvalid(AI_TYPE_KEY, section);
            return null;
        }
        AIType aiType = AIType.findById(aiTypeId);
        if (aiType == null) {
            LOGGER.warn("The AI type id {} does not exist!", aiTypeId);
            return null;
        }
        return aiType;
    }

    /**
     * Gets npc's experience.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's experience, or 0 if the key {@code EXPERIENCE_KEY} is missing or has an invalid value
     */
    private int getExperience(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + EXPERIENCE_KEY, 0);
    }

    /**
     * Gets npc's gold.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's gold, or 0 if the key {@code GOLD_KEY} is missing or has an invalid value
     */
    private int getGold(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + GOLD_KEY, 0);
    }

    /**
     * Gets npc's min hp.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's a min hp, or 0 if the key {@code MIN_HP_KEY} is missing or has an invalid value
     */
    private int getMinHP(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_HP_KEY, 0);
    }

    /**
     * Gets npc's max hp.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's max hp, or 0 if the key {@code MAX_HP_KEY} is missing or has an invalid value
     */
    private int getMaxHP(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_HP_KEY, 0);
    }

    /**
     * Gets npc's min hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's min hit, or 0 if the key {@code MIN_HIT_KEY} is missing or has an invalid value
     */
    private int getMinHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_HIT_KEY, 0);
    }

    /**
     * Gets npc's max hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's max hit, or 0 if the key {@code MAX_HIT_KEY} is missing or has an invalid value
     */
    private int getMaxHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_HIT_KEY, 0);
    }

    /**
     * Gets npc's attack.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's attack, or 0 if the key {@code ATTACK_KEY} is missing or has an invalid value
     */
    private short getAttack(INIConfiguration ini, String section) {
        return (short) IniUtils.getInt(ini, section + "." + ATTACK_KEY, 0);
    }

    /**
     * Gets npc's defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's defense, or 0 if the key {@code DEFENSE_KEY} is missing or has an invalid value
     */
    private short getDefense(INIConfiguration ini, String section) {
        // TODO Es la mejor practica hacer un cast a short?
        return (short) IniUtils.getInt(ini, section + "." + DEFENSE_KEY, 0);
    }

    /**
     * Gets npc's magic defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's magic defense, or 0 if the key {@code MAGIC_DEFENSE_KEY} is missing or has an invalid value
     */
    private short getMagicDefense(INIConfiguration ini, String section) {
        return (short) IniUtils.getInt(ini, section + "." + MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets npc's evasion.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the npc's evasion, or 0 if the key {@code EVASION_KEY} is missing or has an invalid value
     */
    private short getEvasion(INIConfiguration ini, String section) {
        return (short) IniUtils.getInt(ini, section + "." + EVASION_KEY, 0);
    }

    /**
     * Check if the npc returns to its original position.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc returns to its original position, or false if the key {@code RETURNING_KEY} is missing or has an
     * invalid value
     */
    private boolean isReturning(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RETURNING_KEY, false);
    }

    /**
     * Checks if the npc has restockable keys.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc has restockable keys, or false if the key {@code RESTOCKABLE_KEY} is missing or has an invalid
     * value
     */
    private boolean isRestockable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RESTOCKABLE_KEY, false);
    }

    /**
     * Checks if the npc is merchant.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is merchant, or false if the key {@code MERCHANT_KEY} is missing or has an invalid value
     */
    private boolean isMerchant(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + MERCHANT_KEY, false);
    }

    /**
     * Checks if the npc is aquatic.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is aquatic, or false if the key {@code AQUATIC_KEY} is missing or has an invalid value
     */
    private boolean isAquatic(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + AQUATIC_KEY, false);
    }

    /**
     * Checks if the npc is attackable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is attackable, or false if the key {@code ATTACKABLE_KEY} is missing or has an invalid value
     */
    private boolean isAttackable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + ATTACKABLE_KEY, false);
    }

    /**
     * Checks if the npc is hostile.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is hostile, or false if the key {@code HOSTILE_KEY} is missing or has an invalid value
     */
    private boolean isHostile(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + HOSTILE_KEY, false);
    }

    /**
     * Checks if the npc is tameable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is tameable, or false if the key {@code TAMEABLE_KEY} is missing or has an invalid value
     */
    private boolean isTameable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + TAMEABLE_KEY, false);
    }

    /**
     * Checks if the npc is poisonous.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is poisonous, or false if the key {@code POISONOUS_KEY} is missing or has an invalid value
     */
    private boolean isPoisonous(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + POISONOUS_KEY, false);
    }

    /**
     * Checks if the npc is unparalyzable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the npc is unparalyzable, or false if the key {@code UNPARALYZABLE_KEY} is missing or has an invalid value
     */
    private boolean isUnparalyzable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + UNPARALYZABLE_KEY, false);
    }

    private void logKeyNotFoundOrInvalid(String key, String section) {
        LOGGER.warn("The key '{}' was not found in section [{}] or its value is invalid!", key, section);
    }

}
