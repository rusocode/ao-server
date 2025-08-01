package com.ao.data.dao.ini;

import com.ao.data.dao.WorldObjectPropertiesDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.worldobject.ResourceSourceType;
import com.ao.model.worldobject.WoodType;
import com.ao.model.worldobject.WorldObjectType;
import com.ao.model.worldobject.properties.*;
import com.ao.model.worldobject.properties.manufacture.Manufacturable;
import com.ao.model.worldobject.properties.manufacture.ManufactureType;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Ini-backed implementation of the World Object DAO.
 */

public class WorldObjectPropertiesDAOIni implements WorldObjectPropertiesDAO {

    protected static final String INIT_HEADER = "INIT";
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldObjectPropertiesDAOIni.class);
    private static final int MAX_SOUNDS = 3;
    private static final String NUM_OBJECTS_KEY = "NumOBJs";

    private static final String OBJECT_SECTION_PREFIX = "OBJ";
    private static final String NAME_KEY = "Name";
    private static final String GRAPHIC_KEY = "GrhIndex";
    private static final String OBJECT_TYPE_KEY = "ObjType";
    private static final String VALUE_KEY = "Valor";
    private static final String WOODWORKING_DIFFICULTY_KEY = "SkCarpinteria";
    private static final String IRONWORKING_DIFFICULTY_KEY = "SkHerreria";
    private static final String NEWBIE_KEY = "Newbie";
    private static final String EQUIPPED_GRAPHIC_KEY = "NumRopaje";
    private static final String MIN_DEF_KEY = "MinDef";
    private static final String MAX_DEF_KEY = "MaxDef";
    private static final String MIN_MAGIC_DEF_KEY = "DefensaMagicaMin";
    private static final String MAX_MAGIC_DEF_KEY = "DefensaMagicaMax";
    private static final String DWARF_KEY = "RazaEnana";
    private static final String DARK_ELF_KEY = "RazaDrow";
    private static final String ELF_KEY = "RazaElfa";
    private static final String GNOME_KEY = "RazaGnoma";
    private static final String HUMAN_KEY = "RazaHumana";
    private static final String FORBIDDEN_ARCHETYPE_PREFIX = "CP";
    private static final String MIN_HIT_KEY = "MinHit";
    private static final String MAX_HIT_KEY = "MaxHit";
    private static final String STABS_KEY = "Apuñala";
    private static final String PIERCING_DAMAGE_KEY = "Refuerzo";
    private static final String IS_RANGED_WEAPON_KEY = "Proyectil";
    private static final String NEEDS_AMMUNITION_KEY = "Municiones";
    private static final String MAGIC_POWER_KEY = "StaffPower";
    private static final String DAMAGE_BONUS_KEY = "StaffDamageBonus";
    private static final String USAGE_DIFFICULTY_KEY = "MinSkill";
    private static final String HUNGER_KEY = "MinHam";
    private static final String THIRST_KEY = "MinAgu";
    private static final String SOUND_PREFIX = "SND";
    private static final String RADIUS_KEY = "Radio";
    private static final String POTION_TYPE_KEY = "TipoPocion";
    private static final String MIN_MODIFIER = "MinModificador";
    private static final String MAX_MODIFIER = "MaxModificador";
    private static final String MODIFIER_TIME = "DuracionEfecto";
    private static final String EQUIPPED_WEAPON_GRAPHIC_KEY = "Anim";
    private static final String UNGRABABLE_KEY = "Agarrable";
    private static final String SPELL_INDEX_KEY = "HechizoIndex";
    private static final String MINERAL_INDEX_KEY = "MineralIndex";
    private static final String CODE_KEY = "Clave";
    private static final String OPEN_KEY = "Abierta";
    private static final String LOCKED_KEY = "Llave";
    private static final String OPEN_OBJECT_ID_KEY = "IndexAbierta";
    private static final String CLOSED_OBJECT_ID_KEY = "IndexCerrada";
    private static final String RESPAWNEABLE_KEY = "Crucial";
    private static final String FALLS_KEY = "NoSeCae";
    private static final String NO_LOG_KEY = "NoLog";
    private static final String BIG_GRAPHIC_KEY = "VGrande";
    private static final String TEXT_KEY = "Texto";
    private static final String FORUM_NAME_KEY = "ID";
    private static final String BACKPACK_TYPE_KEY = "MochilaType";
    private static final String INGOT_OBJECT_INDEX_KEY = "Lingoteindex";
    private static final String WOOD_AMOUNT_KEY = "Madera";
    private static final String ELVEN_WOOD_AMOUNT_KEY = "MaderaElfica";
    private static final String INGOT_GOLD_AMOUNT_KEY = "LingO";
    private static final String INGOT_SILVER_AMOUNT_KEY = "LingP";
    private static final String INGOT_IRON_AMOUNT_KEY = "LingH";

    // Horrible, but it's completely hardwired in an old VB version, and can't be induced from the dat
    private static final int WOOD_INDEX = 58;
    private static final int ELVEN_WOOD_INDEX = 1006;
    private static final int[] INGOTES = {386, 387, 388};

    private static final Map<String, UserArchetype> archetypesByName;

    static {

        // Populate aliases from Spanish config files to internal types
        archetypesByName = new HashMap<>();

        archetypesByName.put("MAGO", UserArchetype.MAGE);
        archetypesByName.put("CLERIGO", UserArchetype.CLERIC);
        archetypesByName.put("GUERRERO", UserArchetype.WARRIOR);
        archetypesByName.put("ASESINO", UserArchetype.ASSASIN);
        archetypesByName.put("LADRON", UserArchetype.THIEF);
        archetypesByName.put("BARDO", UserArchetype.BARD);
        archetypesByName.put("DRUIDA", UserArchetype.DRUID);
        archetypesByName.put("BANDIDO", UserArchetype.BANDIT);
        archetypesByName.put("PALADIN", UserArchetype.PALADIN);
        archetypesByName.put("CAZADOR", UserArchetype.HUNTER);
        // se are not used in AO 0.13.x and later, but left in case someone wants them ^_^
//		archetypesByName.put("PESCADOR", UserArchetype.FISHER);
//		archetypesByName.put("HERRERO", UserArchetype.BLACKSMITH);
//		archetypesByName.put("LEÑADOR", UserArchetype.LUMBERJACK);
//		archetypesByName.put("MINERO", UserArchetype.MINER);
//		archetypesByName.put("CARPINTERO", UserArchetype.CARPENTER);
        archetypesByName.put("PIRATA", UserArchetype.PIRATE);
        archetypesByName.put("TRABAJADOR", UserArchetype.PIRATE);
    }

    private final String objectsFilePath;
    private final int itemsPerRow;

    private Map<Integer, Manufacturable> manufacturables;
    private WorldObjectProperties[] worldObjectProperties;

    /**
     * Creates a new WorldObjectDAOIni instance.
     *
     * @param objectsFilePath path to the file with all objects definitions
     */
    @Inject
    public WorldObjectPropertiesDAOIni(@Named("objectsFilePath") String objectsFilePath,
                                       @Named("itemsPerRow") int itemsPerRow) {
        this.objectsFilePath = objectsFilePath;
        this.itemsPerRow = itemsPerRow;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.data.dao.WorldObjectPropertiesDAO#getAllManufacturables()
     */
    @Override
    public Map<Integer, Manufacturable> getAllManufacturables() throws DAOException {
        if (null == manufacturables) loadAll(); // Force the ini to be loaded!
        return manufacturables;
    }

    /*
     * (non-Javadoc)
     * @see com.ao.data.dao.WorldObjectPropertiesDAO#getWorldObjectProperties(int)
     */
    @Override
    public WorldObjectProperties getWorldObjectProperties(int id) {
        return worldObjectProperties[id - 1];
    }

    /*
     * (non-Javadoc)
     * @see com.ao.data.dao.WorldObjectDAO#retrieveAll()
     */
    @Override
    public void loadAll() throws DAOException {
        Ini iniFile;

        LOGGER.info("Loading all world object properties from ini file.");

        // Reset manufacturables
        manufacturables = new HashMap<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(objectsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file " + objectsFilePath + " was not found in the classpath");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            iniFile = new Ini(reader);
        } catch (Exception e) {
            LOGGER.error("World Object loading failed!", e);
            throw new RuntimeException(e);
        }

        int totalObjects = Integer.parseInt(iniFile.get(INIT_HEADER, NUM_OBJECTS_KEY));

        worldObjectProperties = new WorldObjectProperties[totalObjects];

        for (int i = 1; i <= totalObjects; i++)
            worldObjectProperties[i - 1] = loadObject(i, iniFile);

    }

    /**
     * Creates an object's properties from the given section.
     *
     * @param iniFile ini file that contains all world objects to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadObject(int id, Ini iniFile) {

        //  section of the ini file containing the world object to be loaded
        Section section = iniFile.get(OBJECT_SECTION_PREFIX + id);

        // Make sure its valid
        if (section == null) return null;

        WorldObjectProperties obj = null;
        String name = section.get(NAME_KEY);
        int graphic = Integer.parseInt(section.get(GRAPHIC_KEY));
        int objectType = Integer.parseInt(section.get(OBJECT_TYPE_KEY));
        LegacyWorldObjectType type = LegacyWorldObjectType.valueOf(objectType);

        switch (type) {
            case RING:
            case HELMET:
            case ARMOR:
            case SHIELD:
                obj = loadDefensiveItem(type.getCurrentType(), id, name, graphic, section);
                break;
            case WEAPON:
                obj = loadWeapon(id, name, graphic, section);
                break;
            case ARROW:
                obj = loadAmmunition(type.getCurrentType(), id, name, graphic, section);
                break;
            case BOAT:
                obj = loadBoat(type.getCurrentType(), id, name, graphic, section);
                break;
            case DRINK:
            case FILLED_BOTTLE:
            case EMPTY_BOTTLE:
                obj = loadDrink(type.getCurrentType(), id, name, graphic, section);
                break;
            case USE_ONCE:
                obj = loadFood(type.getCurrentType(), id, name, graphic, section);
                break;
            case MONEY:
            case FORGE:
            case ANVIL:
                obj = loadGenericItem(type.getCurrentType(), id, name, graphic, section);
                break;
            case MUSICAL_INSTRUMENT:
                obj = loadMusicalInstrument(type.getCurrentType(), id, name, graphic, section);
                break;
            case TELEPORT:
                obj = loadTeleport(type.getCurrentType(), id, name, graphic, section);
                break;
            case POTION:
                obj = loadPotion(id, name, graphic, section);
                break;
            case GEMS:
                obj = loadGem(id, name, graphic, section);
                break;
            case FLOWERS:
            case JEWELRY:
            case BOOK:
            case STAIN:
            case CONTAINER:
            case FURNITURE:
            case BONFIRE:
                obj = loadProps(id, name, graphic, section);
                break;
            case PARCHMENT:
                obj = loadParchment(type.getCurrentType(), id, name, graphic, section);
                break;
            case TREE:
            case ELVEN_TREE:
            case MINE:
                obj = loadResourceSource(type.getCurrentType(), id, name, graphic, section, type);
                break;
            case WOOD:
                obj = loadWood(type.getCurrentType(), id, name, graphic, section, id == ELVEN_WOOD_INDEX ? WoodType.ELVEN : WoodType.NORMAL);
                break;
            case KEY:
                obj = loadKey(type.getCurrentType(), id, name, graphic, section);
                break;
            case DOOR:
                obj = loadDoor(type.getCurrentType(), id, name, graphic, section);
                break;
            case SIGN:
                obj = loadSign(type.getCurrentType(), id, name, graphic, section);
                break;
            case FORUM:
                obj = loadForum(type.getCurrentType(), id, name, graphic, section);
                break;
            case BACKPACK:
                obj = loadBackpack(type.getCurrentType(), id, name, graphic, section);
                break;
            case MINERAL:
                obj = loadMineral(type.getCurrentType(), id, name, graphic, section);
                break;
            default:
                LOGGER.error("Unexpected object type found: " + objectType);
        }

        // Check if the item is manufacturable
        if (getManufactureType(section, obj) != null) {
            try {
                manufacturables.put(obj.getId(), loadManufacturable(section, obj));
            } catch (DAOException e) {
                LOGGER.error("Item " + id + " seems like manufacturable, but its not!");
            }
        }

        return obj;
    }

    /**
     * Loads manufacture data from the given section for the provided object.
     *
     * @param section section from which to load the manufacture data
     * @param obj     properties of the object whose manufacture data to laod
     * @return the loaded manufacturable
     */
    private Manufacturable loadManufacturable(Section section,
                                              WorldObjectProperties obj) throws DAOException {

        ManufactureType manufactureType = getManufactureType(section, obj);

        if (null == manufactureType) throw new DAOException("Item is not manufacturable");

        int manufactureDifficulty;

        /*
         * AO's OBJ.dat is totally inconsistent, so MinSkill for Minerals is the required
         * skill level to build ingots from them, while for boats its the required
         * skill level to use them and SkCarpitenria is the skill level required
         * to build them. If you ever edit this format or create a new DAO,
         * I beg you to try to avoid this and be consistent.
         */
        if (obj instanceof MineralProperties) manufactureDifficulty = getUsageDifficulty(section);
        else manufactureDifficulty = getManufactureDifficulty(section);

        int requiredWood = getRequiredWoodForManufacture(section);
        int requiredElvenWood = getRequiredElvenWoodForManufacture(section);
        int requiredGoldIngot = getRequiredGoldIngotsForManufacture(section);
        int requiredSilverIngot = getRequiredSilverIngotsForManufacture(section);
        int requiredIronIngot = getRequiredIronIngotsForManufacture(section);

        return new Manufacturable(obj, manufactureType, manufactureDifficulty,
                requiredWood, requiredElvenWood, requiredGoldIngot,
                requiredSilverIngot, requiredIronIngot);
    }

    /**
     * Determines the manufacturable type of the item (if any).
     *
     * @param section section from which to read the object's value
     * @param obj     object's properties
     * @return the obejct's manufacturable type
     */
    private ManufactureType getManufactureType(Section section,
                                               WorldObjectProperties obj) {

        if (obj instanceof MineralProperties) return ManufactureType.BLACKSMITH;

        String value = section.get(WOODWORKING_DIFFICULTY_KEY);

        if (value != null) return ManufactureType.WOODWORK;

        value = section.get(IRONWORKING_DIFFICULTY_KEY);

        if (value != null) return ManufactureType.IRONWORK;

        return null;
    }

    /**
     * Creates a wood's properties from the given section.
     *
     * @param worldObjectType object's type
     * @param id              object's id
     * @param name            object's name
     * @param graphic         object's graphic
     * @param section         section of the ini file containing the world object to be loaded
     * @param woodType        type of wood this is
     * @return the created world object properties
     */
    private WorldObjectProperties loadWood(WorldObjectType worldObjectType, int id, String name, int graphic, Section section, WoodType woodType) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new WoodProperties(worldObjectType, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, woodType);
    }

    /**
     * Creates a resource source's properties from the given section.
     *
     * @param type       object's type
     * @param id         object's id
     * @param name       object's name
     * @param graphic    object's graphic
     * @param section    section of the ini file containing the world object to be loaded
     * @param legactType legacy type of the item
     * @return the created world object properties
     */
    private WorldObjectProperties loadResourceSource(WorldObjectType type, int id, String name, int graphic, Section section, LegacyWorldObjectType legactType) {

        int resourceId = -1;

        // Get the resource id
        ResourceSourceType resourceSourceType = null;
        switch (legactType) {
            case TREE:
                resourceId = WOOD_INDEX;
                resourceSourceType = ResourceSourceType.TREE;
                break;
            case ELVEN_TREE:
                resourceId = ELVEN_WOOD_INDEX;
                resourceSourceType = ResourceSourceType.TREE;
                break;
            case MINE:
                resourceId = getMineralIndex(section);
                resourceSourceType = ResourceSourceType.MINE;
                break;
            default:
                LOGGER.error("Unexpected resource source of type " + type.name());
        }

        return new ResourceSourceProperties(type, id, name, graphic, resourceId, resourceSourceType);
    }

    /**
     * Creates a defensive item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadDefensiveItem(WorldObjectType type, int id, String name, int graphic,
                                                    Section section) {

        int value = getValue(section);
        int manufactureDifficulty = getManufactureDifficulty(section);
        boolean newbie = getNewbie(section);
        int equippedGraphic = getEquippedGraphic(section);
        int minDef = getMinDef(section);
        int maxDef = getMaxDef(section);
        int minMagicDef = getMinMagicDef(section);
        int maxMagicDef = getMaxMagicDef(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new DefensiveItemProperties(type, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, minDef, maxDef, minMagicDef, maxMagicDef);
    }

    /**
     * Creates a teleport's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadTeleport(WorldObjectType type, int id, String name, int graphic,
                                               Section section) {
        int radius = getRadius(section);
        return new TeleportProperties(type, id, name, graphic, radius);
    }

    /**
     * Creates a prop's properties from the given section.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadProps(int id, String name, int graphic, Section section) {

        // Is it grabable or is it just a prop?
        if (!isGrabable(section))
            // It's a prop
            return new WorldObjectProperties(WorldObjectType.PROP, id, name, graphic);

        return loadGenericItem(WorldObjectType.GRABABLE_PROP, id, name, graphic, section);
    }

    /**
     * Creates a generic item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the generic item created
     */
    private WorldObjectProperties loadGenericItem(WorldObjectType type, int id, String name, int graphic,
                                                  Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new ItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable);
    }

    /**
     * Creates a potion's properties from the given section.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadPotion(int id, String name, int graphic,
                                             Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        PotionType potionType = getPotionType(section);

        // Is it a death potion?
        if (potionType == PotionType.DEATH)
            return new ItemProperties(WorldObjectType.DEATH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable);
        else if (potionType == PotionType.POISON) // Poison potion?
            return new ItemProperties(WorldObjectType.POISON_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable);

        int minModifier = getMinModifier(section);
        int maxModifier = getMaxModifier(section);

        // Is it an HP potion?
        if (potionType == PotionType.HP)
            return new StatModifyingItemProperties(WorldObjectType.HP_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, minModifier, maxModifier);
        else if (potionType == PotionType.MANA) // Mana potion?
            return new StatModifyingItemProperties(WorldObjectType.MANA_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, minModifier, maxModifier);

        int effectTime = getEffectTime(section);

        // Strength potion?
        if (potionType == PotionType.STRENGTH)
            return new TemporalStatModifyingItemProperties(WorldObjectType.STRENGTH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, minModifier, maxModifier, effectTime);
        else if (potionType == PotionType.DEXTERITY) // dexterity potion?
            return new TemporalStatModifyingItemProperties(WorldObjectType.DEXTERITY_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, minModifier, maxModifier, effectTime);

        // This should never happen...
        LOGGER.error("Parsed a potion with an unmatched potion type: " + potionType.name());
        return null;
    }

    /**
     * Creates a musical instrument's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadMusicalInstrument(WorldObjectType type, int id, String name, int graphic,
                                                        Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        int equippedGraphic = getEquippedGraphic(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        List<Integer> sounds = getSounds(section);

        return new MusicalInstrumentProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, sounds);
    }

    /**
     * Creates a boat item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadBoat(WorldObjectType type, int id, String name, int graphic,
                                           Section section) {

        int value = getValue(section);
        int manufactureDifficulty = getManufactureDifficulty(section);
        boolean newbie = getNewbie(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);
        int equippedGraphic = getEquippedGraphic(section);
        int minDef = getMinDef(section);
        int maxDef = getMaxDef(section);
        int minMagicDef = getMinMagicDef(section);
        int maxMagicDef = getMaxMagicDef(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        int usageDifficulty = getUsageDifficulty(section);
        int minHit = getMinHit(section);
        int maxHit = getMaxHit(section);

        return new BoatProperties(type, id, name, graphic, value, usageDifficulty, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, minDef, maxDef, minMagicDef, maxMagicDef, minHit, maxHit);
    }


    /**
     * Creates a food item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadFood(WorldObjectType type, int id, String name, int graphic,
                                           Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        int modifier = getHunger(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new StatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, modifier, modifier);
    }

    /**
     * Creates a drink item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadDrink(WorldObjectType type, int id, String name, int graphic,
                                            Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        int modifier = 0;
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        if (type != WorldObjectType.EMPTY_BOTTLE) getThirst(section);


        int emptyObjectId = getOpenObjectId(section);
        int filledObjectId = getClosedObjectId(section);

        // Is it refillable?
        if (emptyObjectId != 0 && filledObjectId != 0) {

            boolean filled = id == filledObjectId;

            RefillableStatModifyingItemProperties otherObjectProperties = null;

            // Only take the other object when we are loading the latest of both, to make sure the other one is already loaded
            if (filled && id > emptyObjectId)
                otherObjectProperties = (RefillableStatModifyingItemProperties) worldObjectProperties[emptyObjectId - 1];
            else if (!filled && id > filledObjectId)
                otherObjectProperties = (RefillableStatModifyingItemProperties) worldObjectProperties[filledObjectId - 1];

            return new RefillableStatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, modifier, modifier, filled, otherObjectProperties);
        } else
            return new StatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, modifier, modifier);

    }

    /**
     * Creates a weapon's properties from the given section.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadWeapon(int id, String name, int graphic,
                                             Section section) {

        // Load basic data
        int value = getValue(section);
        int manufactureDifficulty = getManufactureDifficulty(section);
        boolean newbie = getNewbie(section);
        int equippedGraphic = getEquippedGraphic(section);
        int minHit = getMinHit(section);
        int maxHit = getMaxHit(section);
        boolean stabs = getStabs(section);
        int piercingDamage = getPiercingDamage(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        // Is it a ranged weapon?
        if (isRangedWeapon(section)) {
            boolean needsAmmunition = getNeedsAmmunition(section);
            return new RangedWeaponProperties(WorldObjectType.RANGED_WEAPON, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, stabs, piercingDamage, minHit, maxHit, needsAmmunition);
        } else if (isStaff(section)) { // Is it a staff?
            int magicPower = getMagicPower(section);
            int damageBonus = getDamageBonus(section);
            return new StaffProperties(WorldObjectType.STAFF, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, stabs, piercingDamage, minHit, maxHit, magicPower, damageBonus);
        }
        // Just a normal weapon
        return new WeaponProperties(WorldObjectType.WEAPON, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, stabs, piercingDamage, minHit, maxHit);
    }

    /**
     * Creates an ammunition item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadAmmunition(WorldObjectType type, int id, String name, int graphic,
                                                 Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        int equippedGraphic = getEquippedGraphic(section);
        int minHit = getMinHit(section);
        int maxHit = getMaxHit(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new AmmunitionProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, minHit, maxHit);
    }

    /**
     * Creates a parchment item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadParchment(WorldObjectType type, int id, String name, int graphic, Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);
        int spellIndex = getSpellIndex(section);
        // TODO Create the Spell implementation.
        return new ParchmentProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, null);
    }

    /**
     * Creates a key's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadKey(WorldObjectType type, int id, String name, int graphic,
                                          Section section) {

        int value = getValue(section);
        int manufactureDifficulty = getManufactureDifficulty(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        int code = getCode(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new KeyProperties(type, id, name, graphic, value, manufactureDifficulty, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, code);
    }

    /**
     * Creates a door's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadDoor(WorldObjectType type, int id, String name, int graphic,
                                           Section section) {

        boolean open = getOpen(section);
        boolean locked = getLocked(section);
        int code = getCode(section);

        int openObjectId = getOpenObjectId(section);
        int closedObjectId = getClosedObjectId(section);

        DoorProperties otherObjectProperties = null;

        if (openObjectId == 0 || closedObjectId == 0) {
            LOGGER.error("Invalid door definition for id " + id + ". Ids for open and closed states are required.");
            return null;
        }

        // Only take the other object when we are loading the latest of both, to make sure the other one is already loaded
        if (open && id > closedObjectId)
            otherObjectProperties = (DoorProperties) worldObjectProperties[closedObjectId - 1];
        else if (!open && id > openObjectId)
            otherObjectProperties = (DoorProperties) worldObjectProperties[openObjectId - 1];

        return new DoorProperties(type, id, name, graphic, open, locked, code, otherObjectProperties);
    }

    /**
     * Creates a sign item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadSign(WorldObjectType type, int id, String name, int graphic, Section section) {
        int bigGraphic = getBigGraphic(section);
        String text = getText(section);
        return new SignProperties(type, id, name, graphic, bigGraphic, text);
    }

    /**
     * Creates a forum item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadForum(WorldObjectType type, int id, String name, int graphic, Section section) {
        String forumName = getForumName(section);
        return new ForumProperties(type, id, name, graphic, forumName);
    }

    /**
     * Creates a backpack item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadBackpack(WorldObjectType type, int id, String name, int graphic, Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        int equippedGraphic = getEquippedGraphic(section);
        int backpackType = getBackpackType(section);
        int slots = getAmountForBackpackType(backpackType);

        return new BackpackProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, equippedGraphic, slots);
    }

    /**
     * Creates a backpack item's properties from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the created world object properties
     */
    private WorldObjectProperties loadMineral(WorldObjectType type, int id, String name, int graphic, Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        int ingotObjectIndex = getIngotObjectIndex(section);

        return new MineralProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable, ingotObjectIndex);
    }

    /**
     * Creates an ingot property from the given section.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the ingot item created
     */
    private WorldObjectProperties loadIngot(WorldObjectType type, int id, String name, int graphic, Section section) {

        int value = getValue(section);
        boolean newbie = getNewbie(section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(section);
        List<Race> forbiddenRaces = getForbiddenRaces(section);
        boolean noLog = getNoLog(section);
        boolean falls = getFalls(section);
        boolean respawneable = getRespawneable(section);

        return new ItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, falls, respawneable);
    }

    /**
     * Creates a gem property from the given section.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param section section of the ini file containing the world object to be loaded
     * @return the gem item created
     */
    private WorldObjectProperties loadGem(int id, String name, int graphic, Section section) {
        for (int i = 0; i < INGOTES.length; i++)
            if (INGOTES[i] == id) return loadIngot(WorldObjectType.INGOT, id, name, graphic, section);
        return loadProps(id, name, graphic, section);
    }

    /**
     * Retrieves a list of all forbidden archetypes for this item, if any. Null if all are permited.
     *
     * @param section section from which to parse the forbidden archetypes
     * @return the list of forbidden archetypes for the item, or null if none
     */
    private List<UserArchetype> getForbiddenArchetypes(Section section) {
        String data;
        List<UserArchetype> forbiddenArchetypes = new LinkedList<UserArchetype>();
        int total = UserArchetype.values().length;

        for (int i = 1; i <= total; i++) {
            data = section.get(FORBIDDEN_ARCHETYPE_PREFIX + i);
            if (data != null) {
                if (archetypesByName.containsKey(data)) forbiddenArchetypes.add(archetypesByName.get(data));
                else LOGGER.error("Unexpected forbidden class loading object: " + data);  // This shouldn't happen!!!
            }
        }

        if (forbiddenArchetypes.size() == 0) return null;

        return forbiddenArchetypes;
    }

    /**
     * Retrieves a list of all forbidden races for this item, if any. Null if all are permited.
     *
     * @param section section from which to parse the forbidden races
     * @return the list of forbidden races for the item, or null if none
     */
    private List<Race> getForbiddenRaces(Section section) {
        String data;
        List<Race> forbiddenRaces = new LinkedList<>();

        data = section.get(DWARF_KEY);
        if (data != null && !"0".equals(data)) forbiddenRaces.add(Race.DWARF);

        data = section.get(DARK_ELF_KEY);
        if (data != null && !"0".equals(data)) forbiddenRaces.add(Race.DARK_ELF);

        data = section.get(ELF_KEY);
        if (data != null && !"0".equals(data)) forbiddenRaces.add(Race.ELF);

        data = section.get(GNOME_KEY);
        if (data != null && !"0".equals(data)) forbiddenRaces.add(Race.GNOME);

        data = section.get(HUMAN_KEY);
        if (data != null && !"0".equals(data)) forbiddenRaces.add(Race.HUMAN);

        if (forbiddenRaces.size() == 0) return null;

        return forbiddenRaces;
    }

    /**
     * Retrieves an object's value from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's value
     */
    private int getValue(Section section) {
        String data = section.get(VALUE_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's manufacture difficulty from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's manufacture difficulty
     */
    private int getManufactureDifficulty(Section section) {
        String value = section.get(WOODWORKING_DIFFICULTY_KEY);
        if (value == null) {
            value = section.get(IRONWORKING_DIFFICULTY_KEY);
            if (value == null) return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * Retrieves the amount of wood required for manufacturing an item.
     *
     * @param section section from which to read the object's value
     * @return the object's required wood for manufacture
     */
    private int getRequiredWoodForManufacture(Section section) {
        String value = section.get(WOOD_AMOUNT_KEY);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    /**
     * Retrieves the amount of elven wood required for manufacturing an item.
     *
     * @param section section from which to read the object's value
     * @return the object's required elven wood for manufacture
     */
    private int getRequiredElvenWoodForManufacture(Section section) {
        String value = section.get(ELVEN_WOOD_AMOUNT_KEY);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    /**
     * Retrieves the amount of gold ingots required for manufacturing an item.
     *
     * @param section section from which to read the object's value
     * @return the obejct's required gold ingots for manufacture
     */
    private int getRequiredGoldIngotsForManufacture(Section section) {
        String value = section.get(INGOT_GOLD_AMOUNT_KEY);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    /**
     * Retrieves the amount of silver ingots required for manufacturing an item.
     *
     * @param section section from which to read the object's value
     * @return the object required silver ingots for manufacture
     */
    private int getRequiredSilverIngotsForManufacture(Section section) {
        String value = section.get(INGOT_SILVER_AMOUNT_KEY);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    /**
     * Retrieves the amount of iron ingots required for manufacturing an item.
     *
     * @param section section from which to read the object's value
     * @return the object required iron ingots for manufacture
     */
    private int getRequiredIronIngotsForManufacture(Section section) {
        String value = section.get(INGOT_IRON_AMOUNT_KEY);
        if (value == null) return 0;
        return Integer.parseInt(value);
    }

    /**
     * Checks if an object is for newbies from its section.
     *
     * @param section section from which to read the object's value
     * @return true if the object is for newbies, false otherwise
     */
    private boolean getNewbie(Section section) {
        String data = section.get(NEWBIE_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if an object stabs from its section.
     *
     * @param section section from which to read the object's value
     * @return true if the object stabs, false otherwise
     */
    private boolean getStabs(Section section) {
        String data = section.get(STABS_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Retrieves an object's equipped graphic from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's equipped graphic
     */
    private int getEquippedGraphic(Section section) {
        String data = section.get(EQUIPPED_GRAPHIC_KEY);
        if (data == null) {
            data = section.get(EQUIPPED_WEAPON_GRAPHIC_KEY);
            if (data == null) return 0;
        }
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's minimum defense from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's minimum defense
     */
    private int getMinDef(Section section) {
        String data = section.get(MIN_DEF_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's maximum defense from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's maximum defense
     */
    private int getMaxDef(Section section) {
        String data = section.get(MAX_DEF_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's minimum magic defense from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's minimum magic defense
     */
    private int getMinMagicDef(Section section) {
        String data = section.get(MIN_MAGIC_DEF_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's maximum magic defense from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's maximum magic defense
     */
    private int getMaxMagicDef(Section section) {
        String data = section.get(MAX_MAGIC_DEF_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's minimum hit from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's minimum hit
     */
    private int getMinHit(Section section) {
        String data = section.get(MIN_HIT_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's maximum hit from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's maximum hit
     */
    private int getMaxHit(Section section) {
        String data = section.get(MAX_HIT_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's hunger restored from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's hunger restored
     */
    private int getHunger(Section section) {
        return Integer.parseInt(section.get(HUNGER_KEY));
    }

    /**
     * Retrieves an object's thirst restored from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's thirst restored
     */
    private int getThirst(Section section) {
        return Integer.parseInt(section.get(THIRST_KEY));
    }

    /**
     * Retrieves an object's piercing damage from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's piercing damage
     */
    private int getPiercingDamage(Section section) {
        String data = section.get(PIERCING_DAMAGE_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's magic power from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's magic power
     */
    private int getMagicPower(Section section) {
        return Integer.parseInt(section.get(MAGIC_POWER_KEY));
    }

    /**
     * Retrieves an object's damage bonus from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's damage bonus
     */
    private int getDamageBonus(Section section) {
        return Integer.parseInt(section.get(DAMAGE_BONUS_KEY));
    }

    /**
     * Retrieves an object's usage difficulty from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's usage difficulty
     */
    private int getUsageDifficulty(Section section) {
        String data = section.get(USAGE_DIFFICULTY_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Checks if the section corresponds to a ranged weapon.
     *
     * @param section section for the item to check
     * @return true if the item is a ranged weapon, false otherwise
     */
    private boolean isRangedWeapon(Section section) {
        String data = section.get(IS_RANGED_WEAPON_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section corresponds to a staff.
     *
     * @param section section for the item to check
     * @return true if the item is a staff, false otherwise
     */
    private boolean isStaff(Section section) {
        String data = section.get(MAGIC_POWER_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Checks if the section corresponds to a staff.
     *
     * @param section section for the item to check
     * @return true if the item is a staff, false otherwise
     */
    private boolean isGrabable(Section section) {
        /*
         * People that wrote the original code in VB weren't precisely consistent...
         * So Agarrable=1 means "not grabable" just the opposite as expected.
         * All other values mean grabable.
         */
        String data = section.get(UNGRABABLE_KEY);
        return data == null || !"1".equals(data);
    }

    /**
     * Checks if the object requires ammunition.
     *
     * @param section section for the item to check
     * @return true if the item requires ammunition, false otherwise
     */
    private boolean getNeedsAmmunition(Section section) {
        String data = section.get(NEEDS_AMMUNITION_KEY);
        return data != null && !"0".equals(data);
    }

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
     * Retrieves an object's radius from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's radius
     */
    private int getRadius(Section section) {
        String data = section.get(RADIUS_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves an object's potion type from its section.
     *
     * @param section section from which to read the object's value
     * @return the object's potion type
     */
    private PotionType getPotionType(Section section) {
        return PotionType.valueOf(Integer.parseInt(section.get(POTION_TYPE_KEY)));
    }

    /**
     * Retrieves the object's minimum modifier.
     *
     * @param section section from which to read the value
     * @return the object's minimum modifier
     */
    private int getMinModifier(Section section) {
        return Integer.parseInt(section.get(MIN_MODIFIER));
    }

    /**
     * Retrieves the object's maximum modifier.
     *
     * @param section section from which to read the value
     * @return the object's maximum modifier
     */
    private int getMaxModifier(Section section) {
        return Integer.parseInt(section.get(MAX_MODIFIER));
    }

    /**
     * Retrieves the object's modifier time.
     *
     * @param section section from which to read the value
     * @return the object's modifier time
     */
    private int getEffectTime(Section section) {
        return Integer.parseInt(section.get(MODIFIER_TIME));
    }

    /**
     * Retrieves the spell's index.
     *
     * @param section section from which to read the value
     * @return the object's spell index
     */
    private int getSpellIndex(Section section) {
        return Integer.parseInt(section.get(SPELL_INDEX_KEY));
    }

    /**
     * Retrieves the mineral's index.
     *
     * @param section section from which to read the value
     * @return the object's mineral index
     */
    private int getMineralIndex(Section section) {
        return Integer.parseInt(section.get(MINERAL_INDEX_KEY));
    }

    /**
     * Retrieves the key's code.
     *
     * @param section section from which to read the value
     * @return the key's code
     */
    private int getCode(Section section) {
        String data = section.get(CODE_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Checks if the door is open or not.
     *
     * @param section section from which to read the object's value
     * @return true if the door is open, false otherwise
     */
    private boolean getOpen(Section section) {
        String data = section.get(OPEN_KEY);
        return data != null && "0".equals(data);
    }

    /**
     * Checks if the door is locked or not.
     *
     * @param section section from which to read the object's value
     * @return true if the door is locked, false otherwise
     */
    private boolean getLocked(Section section) {
        String data = section.get(LOCKED_KEY);
        return data != null && !"0".equals(data);
    }

    /**
     * Retrieves the closed door's id
     *
     * @param section section from which to read the object's value
     * @return the closed door's id
     */
    private int getClosedObjectId(Section section) {
        String data = section.get(CLOSED_OBJECT_ID_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves the open door's id.
     *
     * @param section section from which to read the object's value
     * @return the open door's id
     */
    private int getOpenObjectId(Section section) {
        String data = section.get(OPEN_OBJECT_ID_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Checks if the object should be logged or not
     *
     * @param section section from which to read the object's value
     * @return true if the object should be logged, false otherwise
     */
    private boolean getNoLog(Section section) {
        String data = section.get(NO_LOG_KEY);
        return data != null && "0".equals(data);
    }

    /**
     * Checks if the object falls or not.
     *
     * @param section section from which to read the object's value
     * @return true if the object falls, false otherwise
     */
    private boolean getFalls(Section section) {
        String data = section.get(FALLS_KEY);
        return data != null && "1".equals(data);
    }

    /**
     * Checks if the object is responsible or not.
     *
     * @param section section from which to read the object's value
     * @return true if the object is respawneable, false otherwise
     */
    private boolean getRespawneable(Section section) {
        String data = section.get(RESPAWNEABLE_KEY);
        return data != null && "1".equals(data);
    }

    /**
     * Retrieves the big graphic.
     *
     * @param section section from which to read the object's value
     * @return the big graphic
     */
    private int getBigGraphic(Section section) {
        String data = section.get(BIG_GRAPHIC_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves the text.
     *
     * @param section section from which to read the object's value
     * @return the text
     */
    private String getText(Section section) {
        String data = section.get(TEXT_KEY);
        if (data == null) return "";
        return data;
    }

    /**
     * Retrieves the text.
     *
     * @param section section from which to read the object's value
     * @return the text
     */
    private String getForumName(Section section) {
        String data = section.get(FORUM_NAME_KEY);
        if (data == null) return "";
        return data;
    }

    /**
     * Retrieves the backpack type.
     *
     * @param section section from which to read the objet's value
     * @return the backpack type
     */
    private int getBackpackType(Section section) {
        String data = section.get(BACKPACK_TYPE_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves the backpack type.
     *
     * @param section section from which to read the objet's value
     * @return the backpack type
     */
    private int getIngotObjectIndex(Section section) {
        String data = section.get(INGOT_OBJECT_INDEX_KEY);
        if (data == null) return 0;
        return Integer.parseInt(data);
    }

    /**
     * Retrieves the amount for the given backpack type
     *
     * @param backpackType backpack type
     * @return the amount for the given backpack type
     */
    private int getAmountForBackpackType(int backpackType) {
        return backpackType * itemsPerRow;
    }

    /**
     * Legacy potion types. Y are separate object types nowadays.
     */
    private enum PotionType {

        DEXTERITY(1),
        STRENGTH(2),
        HP(3),
        MANA(4),
        POISON(5),
        DEATH(6);

        private final int value;

        /**
         * Creates a new PotionType.
         *
         * @param value value corresponding to the potion type. Should be unique
         */
        PotionType(int value) {
            this.value = value;
        }

        /**
         * Retrieves the PotionType associated with the given value.
         *
         * @param value value for which to search for a PotionType
         * @return the matched PotionType, if any
         */
        public static PotionType valueOf(int value) {
            for (PotionType type : PotionType.values())
                if (type.value == value) return type;
            return null;
        }
    }

}
