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
import com.ao.utils.IniUtils;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Ini-backed implementation of the World Object DAO.
 * <p>
 * En las claves de tipo flag (0 o 1, true o false), la claves obvias como por ejemplo {@code droppable=1} (que se puede tirar) no
 * se especifican en {@code objects.dat} ya que seria redundante especificar que la mayoria de objetos si se pueden tirar al
 * suelo, por lo tanto esta clave obtiene el valor "true" por defecto desde el metodo {@code isDroppable()} si la clave no se
 * especifico en el objeto.
 * <p>
 * TODO Se podrian reemplazar los archivos .ini por json para mejor comodidad
 * TODO Si dividieramos el archivo objects.dat en varios archivos especificos de cada objeto, entonces no seria necesario especificar
 * la clave "object_type" para cada objeto
 */

public class WorldObjectPropertiesDAOIni implements WorldObjectPropertiesDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldObjectPropertiesDAOIni.class);

    private static final String INIT_HEADER = "INIT";
    private static final String OBJECT_SECTION_PREFIX = "OBJ";
    private static final String NUM_OBJS_KEY = "num_objs";

    /** Keys for the ini file. */
    private static final String NAME_KEY = "name";
    private static final String GRAPHIC_INDEX_KEY = "graphic_index";
    private static final String OBJECT_TYPE_ID_KEY = "object_type_id";
    private static final String VALUE_KEY = "value";
    private static final String SMITHING_SKILL_KEY = "smithing_skill"; // Antes llamada "SkHerreria"
    private static final String NAVIGATION_SKILL_KEY = "navigation_skill"; // Nueva clave, antes se usaba "MinSkill" (inconsistencia)
    private static final String CARPENTRY_SKILL_KEY = "carpentry_skill"; // Antes llamada "SkCarpinteria"
    private static final String NEWBIE_KEY = "newbie";
    private static final String MIN_ARMOR_DEFENSE_KEY = "min_armor_defense";
    private static final String MAX_ARMOR_DEFENSE_KEY = "max_armor_defense";
    private static final String MIN_MAGIC_DEFENSE_KEY = "min_magic_defense";
    private static final String MAX_MAGIC_DEFENSE_KEY = "max_magic_defense";
    private static final String DWARF_KEY = "dwarf";
    private static final String DARK_ELF_KEY = "dark_elf";
    private static final String ELF_KEY = "elf";
    private static final String GNOME_KEY = "gnome";
    private static final String HUMAN_KEY = "human";
    private static final String FORBIDDEN_ARCHETYPE_KEY = "forbidden_archetype"; // Antes llamada "CP" (clase prohibida)
    private static final String MIN_HIT_KEY = "min_hit";
    private static final String MAX_HIT_KEY = "max_hit";
    private static final String STABBING_KEY = "stabbing"; // Antes llamada "Apuñala"
    private static final String PIERCING_DAMAGE_KEY = "piercing_damage"; // Antes llamada "Refuerzo"
    private static final String MAGIC_POWER_KEY = "magic_power"; // Antes llamada "StaffPower" que tambien se usaba para determinar si el objeto era magico (inconsistencia)
    private static final String MAGICAL_WEAPON_KEY = "magical_weapon"; // Nueva clave, antes se usaba "StaffPower" (inconsistencia)
    private static final String RANGED_WEAPON_KEY = "ranged_weapon"; // Antes llamada "Proyectil"
    /**
     * Puede que sea redundante especificar si el objeto arco tiene municiones, pero esta flag diferencia dos casos dentro de las
     * armas a distancia: armas que consumen municion externa (p. ej., arcos que usan flechas) y armas arrojadizas/autosuficientes
     * (p. ej., cuchillas).
     */
    private static final String AMMO_KEY = "ammo";
    private static final String STAFF_DAMAGE_BONUS_KEY = "staff_damage_bonus";
    private static final String HUNGER_POINTS_KEY = "hunger_points"; // Antes llamada "MinHam"
    private static final String THIRST_POINTS_KEY = "thirst_points"; // Antes llamada "MinAgu"
    private static final String RANGE_KEY = "range"; // Antes llamada "Radio"
    private static final String POTION_TYPE_KEY = "potion_type";
    private static final String MIN_MODIFIER = "min_modifier";
    private static final String MAX_MODIFIER = "max_modifier";
    private static final String EFFECT_DURATION_KEY = "effect_duration";
    private static final String EQUIPPED_ARMOR_GRAPHIC_KEY = "equipped_armor_graphic"; // Antes llamada "NumRopaje"
    private static final String EQUIPPED_WEAPON_GRAPHIC_KEY = "equipped_weapon_graphic"; // Antes llamada "Anim"
    private static final String SPELL_INDEX_KEY = "spell_index";
    private static final String MINERAL_INDEX_KEY = "mineral_index";
    private static final String KEY_KEY = "key";
    private static final String OPEN_KEY = "open";
    private static final String LOCKED_KEY = "locked";
    private static final String OPEN_INDEX_KEY = "open_index";
    private static final String CLOSED_INDEX_KEY = "closed_index";
    private static final String PICKUPABLE_KEY = "pickupable"; // Antes llamada "Agarrable"
    private static final String RESPAWNABLE_KEY = "respawnable"; // Antes llamada "Crucial"
    private static final String DROPPABLE_KEY = "droppable"; // Antes llamada "NoSeCae"
    private static final String NO_LOG_KEY = "no_log"; // TODO Para que mierda es esta clave?
    private static final String BIG_GRAPHIC_KEY = "big_graphic"; // Antes llamada "VGrande" TODO En algunos proyectos de AO, esta propiedad suele nombrarse como "GrhSecundario", pero no entiendo para que es
    private static final String TEXT_KEY = "text";
    private static final String FORUM_NAME_KEY = "forum_name"; // Antes llamda "ID"
    private static final String BACKPACK_TYPE_KEY = "backpack_type";
    private static final String INGOT_INDEX_KEY = "ingot_index";
    private static final String WOOD_KEY = "wood";
    private static final String ELVEN_WOOD_KEY = "elven_wood";
    private static final String GOLD_INGOT_KEY = "gold_ingot";
    private static final String SILVER_INGOT_KEY = "silver_ingot";
    private static final String IRON_INGOT_KEY = "iron_ingot";
    private static final String SOUND_PREFIX_KEY = "sound";

    /** Horrible, but it's completely hardwired in an old VB version and can't be induced from the dat. */
    private static final int WOOD_INDEX = 58;
    private static final int ELVEN_WOOD_INDEX = 1006;
    private static final int[] INGOTS = {386, 387, 388};

    /** Maps names from config files to their corresponding UserArchetype enum value. */
    private static final Map<String, UserArchetype> archetypes;

    static {

        // Populate aliases from Spanish config files to internal types
        archetypes = new HashMap<>();

        archetypes.put("MAGO", UserArchetype.MAGE);
        archetypes.put("CLERIGO", UserArchetype.CLERIC);
        archetypes.put("GUERRERO", UserArchetype.WARRIOR);
        archetypes.put("ASESINO", UserArchetype.ASSASIN);
        archetypes.put("LADRON", UserArchetype.THIEF);
        archetypes.put("BARDO", UserArchetype.BARD);
        archetypes.put("DRUIDA", UserArchetype.DRUID);
        archetypes.put("BANDIDO", UserArchetype.BANDIT);
        archetypes.put("PALADIN", UserArchetype.PALADIN);
        archetypes.put("CAZADOR", UserArchetype.HUNTER);
        // Se are not used in AO 0.13.x and later, but left in case someone wants them ^_^
//		archetypesByName.put("PESCADOR", UserArchetype.FISHER);
//		archetypesByName.put("HERRERO", UserArchetype.BLACKSMITH);
//		archetypesByName.put("LEÑADOR", UserArchetype.LUMBERJACK);
//		archetypesByName.put("MINERO", UserArchetype.MINER);
//		archetypesByName.put("CARPINTERO", UserArchetype.CARPENTER);
        archetypes.put("PIRATA", UserArchetype.PIRATE);
        archetypes.put("TRABAJADOR", UserArchetype.WORKER);
    }

    private final String objectsFilePath;
    private final int itemsPerRow;

    private Map<Integer, Manufacturable> manufacturables;
    private WorldObjectProperties[] worldObjectProperties;

    @Inject
    public WorldObjectPropertiesDAOIni(@Named("objectsFilePath") String objectsFilePath, @Named("itemsPerRow") int itemsPerRow) {
        this.objectsFilePath = objectsFilePath;
        this.itemsPerRow = itemsPerRow;
    }

    @Override
    public void load() throws DAOException {
        INIConfiguration ini = null;
        LOGGER.info("Loading all objects from '{}'", objectsFilePath);
        // Reset manufacturables
        manufacturables = new HashMap<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(objectsFilePath);
        if (inputStream == null)
            throw new IllegalArgumentException("The file '" + objectsFilePath + "' was not found in the classpath!");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ini = new INIConfiguration();
            ini.read(reader);
            LOGGER.info("Objects loaded successfully!");
        } catch (IOException | ConfigurationException e) {
            LOGGER.error("Error loading objects!", e);
            System.exit(-1);
        }

        // Required key
        int numObjs = IniUtils.getRequiredInt(ini, INIT_HEADER + "." + NUM_OBJS_KEY);

        worldObjectProperties = new WorldObjectProperties[numObjs];

        for (int i = 1; i <= numObjs; i++)
            worldObjectProperties[i - 1] = loadObject(i, ini);

    }

    @Override
    public Map<Integer, Manufacturable> getAllManufacturables() throws DAOException {
        if (manufacturables == null) load(); // Force the ini to be loaded!
        return manufacturables;
    }

    @Override
    public WorldObjectProperties getWorldObjectProperties(int id) {
        return worldObjectProperties[id - 1];
    }

    /**
     * Loads a object.
     *
     * @param id  object's id
     * @param ini ini configuration
     * @return new object
     */
    private WorldObjectProperties loadObject(int id, INIConfiguration ini) {

        String section = OBJECT_SECTION_PREFIX + id;

        // TODO Que pasa si la clave "name" no existe?
        String name = IniUtils.getString(ini, section + "." + NAME_KEY, "");
        // TODO Son obligatorias estas claves?
        int graphicIndex = IniUtils.getInt(ini, section + "." + GRAPHIC_INDEX_KEY, -1);
        int objectTypeId = IniUtils.getInt(ini, section + "." + OBJECT_TYPE_ID_KEY, -1);

        LegacyWorldObjectType objectType = LegacyWorldObjectType.findById(objectTypeId);

        if (objectType == null) {
            LOGGER.error("Unknown object_type_id={} for OBJ{}.", objectTypeId, id);
            return null;
        }

        WorldObjectProperties object = null;

        switch (objectType) {
            case RING:
            case HELMET:
            case ARMOR:
            case SHIELD:
                object = loadDefensiveItem(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case WEAPON:
                object = loadWeapon(id, name, graphicIndex, ini, section);
                break;
            case ARROW:
                object = loadAmmunition(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case BOAT:
                object = loadBoat(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case DRINK:
            case FILLED_BOTTLE:
            case EMPTY_BOTTLE:
                object = loadDrink(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case USE_ONCE:
                object = loadFood(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case MONEY:
            case FORGE:
            case ANVIL:
                object = loadGenericItem(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case MUSICAL_INSTRUMENT:
                object = loadMusicalInstrument(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case TELEPORT:
                object = loadTeleport(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case POTION:
                object = loadPotion(id, name, graphicIndex, ini, section);
                break;
            case GEMS:
                object = loadGem(id, name, graphicIndex, ini, section);
                break;
            case FLOWERS:
            case JEWELRY:
            case BOOK:
            case STAIN:
            case CONTAINER:
            case FURNITURE:
            case BONFIRE:
                object = loadProps(id, name, graphicIndex, ini, section);
                break;
            case PARCHMENT:
                object = loadParchment(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case TREE:
            case ELVEN_TREE:
            case MINE:
                object = loadResourceSource(objectType.getObjectType(), id, name, graphicIndex, objectType, ini, section);
                break;
            case WOOD:
                object = loadWood(objectType.getObjectType(), id, name, graphicIndex, id == ELVEN_WOOD_INDEX ? WoodType.ELVEN : WoodType.NORMAL, ini, section);
                break;
            case KEY:
                object = loadKey(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case DOOR:
                object = loadDoor(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case SIGN:
                object = loadSign(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case FORUM:
                object = loadForum(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case BACKPACK:
                object = loadBackpack(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            case MINERAL:
                object = loadMineral(objectType.getObjectType(), id, name, graphicIndex, ini, section);
                break;
            default:
                LOGGER.error("Unexpected object found: {}", objectTypeId);
        }

        // Check if the object is manufacturable
        if (object != null && getManufactureType(ini, section) != null) {
            try {
                manufacturables.put(object.getId(), loadManufacturable(object, ini, section));
            } catch (DAOException e) {
                LOGGER.error("Item {} seems like manufacturable, but its not!", id);
            }
        }

        return object;
    }

    /**
     * Loads manufacture data.
     *
     * @param obj     world object properties
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the manufacture data
     */
    private Manufacturable loadManufacturable(WorldObjectProperties obj, INIConfiguration ini, String section) throws DAOException {

        ManufactureType manufactureType = getManufactureType(ini, section);

        if (null == manufactureType) throw new DAOException("Object is not manufacturable!");

        int manufactureSkill = getManufactureSkill(ini, section);

        int wood = getWood(ini, section);
        int elvenWood = getElvenWood(ini, section);
        int goldIngot = getGoldIngot(ini, section);
        int silverIngot = getSilverIngot(ini, section);
        int ironIngot = getIronIngot(ini, section);

        return new Manufacturable(obj, manufactureType, manufactureSkill, wood, elvenWood, goldIngot, silverIngot, ironIngot);
    }

    /**
     * Gets the manufacture type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the manufacture type or null if no manufacturing type can be determined
     */
    private ManufactureType getManufactureType(INIConfiguration ini, String section) {
        String smithingSkill = section + "." + SMITHING_SKILL_KEY;
        String carpentrySkill = section + "." + CARPENTRY_SKILL_KEY;
        if (IniUtils.getString(ini, carpentrySkill, null) != null) return ManufactureType.CARPENTRY;
        if (IniUtils.getString(ini, smithingSkill, null) != null) return ManufactureType.SMITHING;
        return null;
    }

    /**
     * Load the properties of a wood object.
     *
     * @param type     object's type
     * @param id       object's id
     * @param name     object's name
     * @param graphic  object's graphic
     * @param woodType object's wood type
     * @param ini      ini configuration
     * @param section  section from which to read the value
     * @return an instance of WorldObjectProperties representing the wood properties
     */
    private WorldObjectProperties loadWood(WorldObjectType type, int id, String name, int graphic, WoodType woodType, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new WoodProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, woodType);
    }

    /**
     * Load the properties of a resource source object.
     *
     * @param type       object's type
     * @param id         object's id
     * @param name       object's name
     * @param graphic    object's graphic
     * @param legactType object's legacy type
     * @param ini        ini configuration
     * @param section    section from which to read the value
     * @return an instance of WorldObjectProperties representing the resource source properties
     */
    private WorldObjectProperties loadResourceSource(WorldObjectType type, int id, String name, int graphic, LegacyWorldObjectType legactType, INIConfiguration ini, String section) {
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
                resourceId = getMineralIndex(ini, section);
                resourceSourceType = ResourceSourceType.MINE;
                break;
            default:
                LOGGER.error("Unexpected resource source of type {}", type.name());
        }
        return new ResourceSourceProperties(type, id, name, graphic, resourceId, resourceSourceType);
    }

    /**
     * Load the properties of a defensive item object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the defensive item properties
     */
    private WorldObjectProperties loadDefensiveItem(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        int manufactureSkill = getManufactureSkill(ini, section);
        boolean newbie = isNewbie(ini, section);
        int equippedGraphic = getEquippedGraphic(ini, section);
        int minArmorDefense = getMinArmorDefense(ini, section);
        int maxArmorDefense = getMaxArmorDefense(ini, section);
        int minMagicDefense = getMinMagicDefense(ini, section);
        int maxMagicDefense = getMaxMagicDefense(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new DefensiveItemProperties(type, id, name, graphic, value, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, minArmorDefense, maxArmorDefense, minMagicDefense, maxMagicDefense);
    }

    /**
     * Load the properties of a teleport object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the teleport's properties
     */
    private WorldObjectProperties loadTeleport(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new TeleportProperties(type, id, name, graphic, getRange(ini, section));
    }

    /**
     * Load the properties of a prop object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the prop's properties
     */
    private WorldObjectProperties loadProps(int id, String name, int graphic, INIConfiguration ini, String section) {
        if (!isPickupable(ini, section)) return new WorldObjectProperties(WorldObjectType.PROP, id, name, graphic);
        return loadGenericItem(WorldObjectType.GRABABLE_PROP, id, name, graphic, ini, section);
    }

    /**
     * Load the properties of a generic item object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the generic item properties
     */
    private WorldObjectProperties loadGenericItem(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new ItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);
    }

    /**
     * Load the properties of a potion object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the potion properties
     */
    private WorldObjectProperties loadPotion(int id, String name, int graphic, INIConfiguration ini, String section) {

        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);

        PotionType potionType = getPotionType(ini, section);

        if (potionType == PotionType.DEATH)
            return new ItemProperties(WorldObjectType.DEATH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);
        else if (potionType == PotionType.POISON) // Poison potion?
            return new ItemProperties(WorldObjectType.POISON_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);

        int minModifier = getMinModifier(ini, section);
        int maxModifier = getMaxModifier(ini, section);

        if (potionType == PotionType.HP)
            return new StatModifyingItemProperties(WorldObjectType.HP_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier);
        else if (potionType == PotionType.MANA)
            return new StatModifyingItemProperties(WorldObjectType.MANA_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier);

        int effectDuration = getEffectDuration(ini, section);

        if (potionType == PotionType.STRENGTH)
            return new TemporalStatModifyingItemProperties(WorldObjectType.STRENGTH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier, effectDuration);
        else if (potionType == PotionType.DEXTERITY)
            return new TemporalStatModifyingItemProperties(WorldObjectType.DEXTERITY_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier, effectDuration);

        // This should never happen...
        LOGGER.error("Parsed a potion with an unmatched potion type: {}", potionType.name());
        return null;
    }

    /**
     * Load the properties of a musical instrument object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the musical instrument properties
     */
    private WorldObjectProperties loadMusicalInstrument(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {

        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);

        int equippedGraphic = getEquippedGraphic(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        List<Integer> sounds = getSounds(ini, section);

        return new MusicalInstrumentProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, sounds);
    }

    /**
     * Load the properties of a boat object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the boat properties
     */
    private WorldObjectProperties loadBoat(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        int manufactureSkill = getManufactureSkill(ini, section);
        boolean newbie = isNewbie(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        int equippedGraphic = getEquippedGraphic(ini, section);
        int minArmorDefense = getMinArmorDefense(ini, section);
        int maxArmorDefense = getMaxArmorDefense(ini, section);
        int minMagicDefense = getMinMagicDefense(ini, section);
        int maxMagicDefense = getMaxMagicDefense(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        int navigationSkill = getNavigationSkill(ini, section);
        int minHit = getMinHit(ini, section);
        int maxHit = getMaxHit(ini, section);
        return new BoatProperties(type, id, name, graphic, value, navigationSkill, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, minArmorDefense, maxArmorDefense, minMagicDefense, maxMagicDefense, minHit, maxHit);
    }

    /**
     * Load the properties of a food object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the food properties
     */
    private WorldObjectProperties loadFood(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        int hungerPoints = getHungerPoints(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        // TODO Por que se pasa "hungerPoints" dos veces?
        return new StatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, hungerPoints, hungerPoints);
    }

    /**
     * Load the properties of a drink object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the drink properties
     */
    private WorldObjectProperties loadDrink(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {

        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        int modifier = 0;
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);

        if (type != WorldObjectType.EMPTY_BOTTLE) getThirstPoints(ini, section); // The thirst value is not being used

        int openIndex = getOpenIndex(ini, section);
        int closedIndex = getClosedIndex(ini, section);

        // Is it refillable?
        if (openIndex != 0 && closedIndex != 0) {

            boolean filled = id == closedIndex;

            RefillableStatModifyingItemProperties otherObjectProperties = null;

            // Only take the other object when we are loading the latest of both, to make sure the other one is already loaded
            if (filled && id > openIndex)
                otherObjectProperties = (RefillableStatModifyingItemProperties) worldObjectProperties[openIndex - 1];
            else if (!filled && id > closedIndex)
                otherObjectProperties = (RefillableStatModifyingItemProperties) worldObjectProperties[closedIndex - 1];

            return new RefillableStatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, modifier, modifier, filled, otherObjectProperties);
        } else
            return new StatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, modifier, modifier);

    }

    /**
     * Load the properties of a weapon object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the weapon properties
     */
    private WorldObjectProperties loadWeapon(int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        int manufactureSkill = getManufactureSkill(ini, section);
        boolean newbie = isNewbie(ini, section);
        int equippedGraphic = getEquippedGraphic(ini, section);
        int minHit = getMinHit(ini, section);
        int maxHit = getMaxHit(ini, section);
        boolean stabbing = isStabbing(ini, section);
        int piercingDamage = getPiercingDamage(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        if (isRangedWeapon(ini, section)) {
            boolean ammo = hasAmmo(ini, section);
            return new RangedWeaponProperties(WorldObjectType.RANGED_WEAPON, id, name, graphic, value, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit, ammo);
        } else if (isMagicalWeapon(ini, section)) {
            int magicPower = getMagicPower(ini, section);
            int staffDamageBonus = getStaffDamageBonus(ini, section);
            return new StaffProperties(WorldObjectType.MAGICAL_WEAPON, id, name, graphic, value, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit, magicPower, staffDamageBonus);
        }
        return new WeaponProperties(WorldObjectType.MELEE_WEAPON, id, name, graphic, value, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit);
    }

    /**
     * Load the properties of an ammunition object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the ammunition properties
     */
    private WorldObjectProperties loadAmmunition(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        int equippedGraphic = getEquippedGraphic(ini, section);
        int minHit = getMinHit(ini, section);
        int maxHit = getMaxHit(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new AmmunitionProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, minHit, maxHit);
    }

    /**
     * Load the properties of a parchment object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the parchment properties
     */
    private WorldObjectProperties loadParchment(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        int spellIndex = getSpellIndex(ini, section);
        // TODO Create the Spell implementation
        return new ParchmentProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, null);
    }

    /**
     * Load the properties of a key object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the key's properties
     */
    private WorldObjectProperties loadKey(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        int manufactureSkill = getManufactureSkill(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        int key = getKey(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new KeyProperties(type, id, name, graphic, value, manufactureSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, key);
    }

    /**
     * Load the properties of a door object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the door properties
     */
    private WorldObjectProperties loadDoor(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {

        boolean open = isOpen(ini, section);
        boolean locked = isLocked(ini, section);
        int key = getKey(ini, section);

        int openIndex = getOpenIndex(ini, section);
        int closedIndex = getClosedIndex(ini, section);

        DoorProperties otherObjectProperties = null;

        if (openIndex == 0 || closedIndex == 0) {
            LOGGER.error("Invalid door definition for id {}. Ids for open and closed states are required.", id);
            return null;
        }

        // Only take the other object when we are loading the latest of both, to make sure the other one is already loaded
        if (open && id > closedIndex) otherObjectProperties = (DoorProperties) worldObjectProperties[closedIndex - 1];
        else if (!open && id > openIndex) otherObjectProperties = (DoorProperties) worldObjectProperties[openIndex - 1];

        return new DoorProperties(type, id, name, graphic, open, locked, key, otherObjectProperties);
    }

    /**
     * Load the properties of a sign object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the sign properties
     */
    private WorldObjectProperties loadSign(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int bigGraphic = getBigGraphic(ini, section);
        String text = getText(ini, section);
        return new SignProperties(type, id, name, graphic, bigGraphic, text);
    }

    /**
     * Load the properties of a forum object.
     *
     * @param type    object's type
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the forum properties
     */
    private WorldObjectProperties loadForum(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        String forumName = getForumName(ini, section);
        return new ForumProperties(type, id, name, graphic, forumName);
    }

    /**
     * Loads the properties of a backpack object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the backpack properties
     */
    private WorldObjectProperties loadBackpack(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        int equippedGraphic = getEquippedGraphic(ini, section);
        int backpackType = getBackpackType(ini, section);
        int slots = getAmountForBackpackType(backpackType);
        return new BackpackProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, slots);
    }

    /**
     * Loads the properties of a mineral object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the mineral's properties
     */
    private WorldObjectProperties loadMineral(WorldObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        int ingotIndex = getIngotIndex(ini, section);
        return new MineralProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, ingotIndex);
    }

    /**
     * Loads the properties of an ingot object.
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the ingot's properties
     */
    private WorldObjectProperties loadIngot(int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);
        return new ItemProperties(WorldObjectType.INGOT, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);
    }

    /**
     * Loads the properties of a gem or an ingot.
     * <p>
     * TODO What is gem?
     *
     * @param id      object's id
     * @param name    object's name
     * @param graphic object's graphic
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return an instance of WorldObjectProperties representing the gem or ingot's properties
     */
    private WorldObjectProperties loadGem(int id, String name, int graphic, INIConfiguration ini, String section) {
        for (int ingote : INGOTS)
            if (ingote == id) return loadIngot(id, name, graphic, ini, section);
        return loadProps(id, name, graphic, ini, section);
    }

    /**
     * Gets a list of all forbidden archetypes for this item.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the list of forbidden archetypes for the item or null if none
     */
    private List<UserArchetype> getForbiddenArchetypes(INIConfiguration ini, String section) {
        List<UserArchetype> forbiddenArchetypes = new LinkedList<>();
        for (int i = 1; i <= UserArchetype.values().length; i++) {
            String key = IniUtils.getString(ini, section + "." + FORBIDDEN_ARCHETYPE_KEY + i, null);
            if (key == null) continue; // Ignore a missing or empty key
            UserArchetype archetype = archetypes.get(key);
            if (archetype != null) forbiddenArchetypes.add(archetype);
            else LOGGER.error("Unexpected forbidden archetype loading object: {}", key); // This shouldn't happen!
        }
        return forbiddenArchetypes.isEmpty() ? null : forbiddenArchetypes;
    }

    /**
     * Gets a list of all forbidden races for this item.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the list of forbidden races for the item or null if none
     */
    private List<Race> getForbiddenRaces(INIConfiguration ini, String section) {
        List<Race> forbiddenRaces = new LinkedList<>();
        if (IniUtils.getBoolean(ini, section + "." + DWARF_KEY, false)) forbiddenRaces.add(Race.DWARF);
        if (IniUtils.getBoolean(ini, section + "." + DARK_ELF_KEY, false)) forbiddenRaces.add(Race.DARK_ELF);
        if (IniUtils.getBoolean(ini, section + "." + ELF_KEY, false)) forbiddenRaces.add(Race.ELF);
        if (IniUtils.getBoolean(ini, section + "." + GNOME_KEY, false)) forbiddenRaces.add(Race.GNOME);
        if (IniUtils.getBoolean(ini, section + "." + HUMAN_KEY, false)) forbiddenRaces.add(Race.HUMAN);
        return forbiddenRaces.isEmpty() ? null : forbiddenRaces;
    }

    /**
     * Gets all sounds an object may reproduce.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the list of sounds the object may reproduce
     */
    private List<Integer> getSounds(INIConfiguration ini, String section) {
        int maxSounds = 3;
        List<Integer> sounds = new LinkedList<>();
        for (int i = 1; i <= maxSounds; i++) {
            String key = section + "." + SOUND_PREFIX_KEY + i;
            if (!ini.containsKey(key)) continue; // Avoid warnings for missing items
            int value = IniUtils.getInt(ini, key, -1); // log invalid entries; -1 = discard
            if (value != -1) sounds.add(value);
        }
        return sounds;
    }

    /**
     * Gets object's potion type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's potion type or 0 if missing/invalid
     */
    private PotionType getPotionType(INIConfiguration ini, String section) {
        return PotionType.valueOf(IniUtils.getInt(ini, section + "." + POTION_TYPE_KEY, 0));
    }

    /**
     * Gets object text.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object text or empty string if missing/invalid
     */
    private String getText(INIConfiguration ini, String section) {
        return IniUtils.getString(ini, section + "." + TEXT_KEY, "");
    }

    /**
     * Gets object's forum name.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's forum name or empty string if missing/invalid
     */
    private String getForumName(INIConfiguration ini, String section) {
        return IniUtils.getString(ini, section + "." + FORUM_NAME_KEY, "");
    }

    /**
     * Gets an object's value.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's value or 0 if missing/invalid
     */
    private int getValue(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + VALUE_KEY, 0);
    }

    /**
     * Gets object's manufacture skill.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's manufacture skill or 0 if missing/invalid
     */
    private int getManufactureSkill(INIConfiguration ini, String section) {
        String carpentrySkill = section + "." + CARPENTRY_SKILL_KEY;
        String blacksmithingSkill = section + "." + SMITHING_SKILL_KEY;

        String value = IniUtils.getString(ini, carpentrySkill, null);
        if (value == null) {
            value = IniUtils.getString(ini, blacksmithingSkill, null);
            if (value == null) return 0;
        }
        return NumberUtils.toInt(value, 0);
    }

    /**
     * Gets object's wood.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's wood or 0 if missing/invalid
     */
    private int getWood(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + WOOD_KEY, 0);
    }

    /**
     * Gets object's elven wood.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's elven wood or 0 if missing/invalid
     */
    private int getElvenWood(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + ELVEN_WOOD_KEY, 0);
    }

    /**
     * Gets object's iron ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's iron ingots or 0 if missing/invalid
     */
    private int getIronIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + IRON_INGOT_KEY, 0);
    }

    /**
     * Gets object's silver ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's silver ingots or 0 if missing/invalid
     */
    private int getSilverIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + SILVER_INGOT_KEY, 0);
    }

    /**
     * Gets object's gold ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's gold ingots or 0 if missing/invalid
     */
    private int getGoldIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + GOLD_INGOT_KEY, 0);
    }

    /**
     * Gets object equipped graphic.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object equipped graphic or 0 if missing/invalid
     */
    private int getEquippedGraphic(INIConfiguration ini, String section) {
        String equippedArmorGraphic = section + "." + EQUIPPED_ARMOR_GRAPHIC_KEY;
        String equippedWeaponGraphic = section + "." + EQUIPPED_WEAPON_GRAPHIC_KEY;

        // Try the main key first; if missing/empty, use secondary
        String value = IniUtils.getString(ini, equippedArmorGraphic, null);
        if (value == null) {
            value = IniUtils.getString(ini, equippedWeaponGraphic, null);
            if (value == null) return 0;
        }

        return NumberUtils.toInt(value, 0);
    }

    /**
     * Gets object's min armor defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min defense or 0 if missing/invalid
     */
    private int getMinArmorDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_ARMOR_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's max armor defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max armor defense or 0 if missing/invalid
     */
    private int getMaxArmorDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_ARMOR_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's min magic defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min magic defense or 0 if missing/invalid
     */
    private int getMinMagicDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets an object's max magic defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max magic defense or 0 if missing/invalid
     */
    private int getMaxMagicDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's min hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min hit or 0 if missing/invalid
     */
    private int getMinHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_HIT_KEY, 0);
    }

    /**
     * Gets object's max hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max hit or 0 if missing/invalid
     */
    private int getMaxHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_HIT_KEY, 0);
    }

    /**
     * Gets object's hunger points.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's hunger points or 0 if missing/invalid
     */
    private int getHungerPoints(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + HUNGER_POINTS_KEY, 0);
    }

    /**
     * Gets object thirst points.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object thirst points or 0 if missing/invalid
     */
    private int getThirstPoints(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + THIRST_POINTS_KEY, 0);
    }

    /**
     * Gets object's piercing damage.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's piercing damage or 0 if missing/invalid
     */
    private int getPiercingDamage(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + PIERCING_DAMAGE_KEY, 0);
    }

    /**
     * Gets an object's magic power.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's magic power or 0 if missing/invalid
     */
    private int getMagicPower(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAGIC_POWER_KEY, 0);
    }

    /**
     * Gets object's staff damage bonus.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's staff damage bonus or 0 if missing/invalid
     */
    private int getStaffDamageBonus(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + STAFF_DAMAGE_BONUS_KEY, 0);
    }

    /**
     * Gets object's navigation skill.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's navigation skill or 0 if missing/invalid
     */
    private int getNavigationSkill(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + NAVIGATION_SKILL_KEY, 0);
    }

    /**
     * Gets object's range.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's range or 0 if missing/invalid
     */
    private int getRange(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + RANGE_KEY, 0);
    }

    /**
     * Gets object's min modifier.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min modifier or 0 if missing/invalid
     */
    private int getMinModifier(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_MODIFIER, 0);
    }

    /**
     * Gets object's max modifier.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max modifier or 0 if missing/invalid
     */
    private int getMaxModifier(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_MODIFIER, 0);
    }

    /**
     * Gets object effect duration.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object effect duration or 0 if missing/invalid
     */
    private int getEffectDuration(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + EFFECT_DURATION_KEY, 0);
    }

    /**
     * Gets object's spell index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's spell index or 0 if missing/invalid
     */
    private int getSpellIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + SPELL_INDEX_KEY, 0);
    }

    /**
     * Gets object's mineral index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's mineral index or 0 if missing/invalid
     */
    private int getMineralIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MINERAL_INDEX_KEY, 0);
    }

    /**
     * Gets object's key.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's key or 0 if missing/invalid
     */
    private int getKey(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + KEY_KEY, 0);
    }

    /**
     * Gets object closed index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object closed index or 0 if missing/invalid
     */
    private int getClosedIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + CLOSED_INDEX_KEY, 0);
    }

    /**
     * Gets object open index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object open index or 0 if missing/invalid
     */
    private int getOpenIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + OPEN_INDEX_KEY, 0);
    }

    /**
     * Gets object's big graphic.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's big graphic or 0 if missing/invalid
     */
    private int getBigGraphic(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + BIG_GRAPHIC_KEY, 0);
    }

    /**
     * Gets object's backpack type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's backpack type or 0 if missing/invalid
     */
    private int getBackpackType(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + BACKPACK_TYPE_KEY, 0);
    }

    /**
     * Gets object's ingot index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's ingot index or 0 if missing/invalid
     */
    private int getIngotIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + INGOT_INDEX_KEY, 0);
    }

    /**
     * Gets the amount for the given backpack type
     *
     * @param backpackType backpack type
     * @return the amount for the given backpack type
     */
    private int getAmountForBackpackType(int backpackType) {
        return backpackType * itemsPerRow;
    }

    /**
     * Checks if the object should be logged or not
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object should be logged or false
     */
    private boolean getNoLog(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + NO_LOG_KEY, false);
    }

    /**
     * Checks if the object has ammo.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object has ammo or false
     */
    private boolean hasAmmo(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + AMMO_KEY, false);
    }

    /**
     * Checks if the object is newbie.
     * <p>
     * IMPORTANTE: La clave <b>newbie</b> solo se especifica en objects.dat para los objetos newbies con el valor 1 (newbie=1 es
     * igual a true), es decir que esta clave es opcional ya que no hace falta especificar newbie=0 para TODOS los otros objetos
     * ya que para las claves faltantes, newbie=0 en este caso, las maneja con un valor false por defecto. Esto se hace para
     * evitar tener que especificar la clave <b>newbie</b> en todos los objetos que no son newbies.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is newbie or false
     */
    private boolean isNewbie(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + NEWBIE_KEY, false);
    }

    /**
     * Checks if the object is stabbing.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is stabbing or false
     */
    private boolean isStabbing(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + STABBING_KEY, false);
    }

    /**
     * Checks if the object is a magical weapon.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is a magical weapon or false
     */
    private boolean isMagicalWeapon(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + MAGICAL_WEAPON_KEY, false);
    }

    /**
     * Checks if the object is a ranged weapon.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is a ranged weapon or false
     */
    private boolean isRangedWeapon(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RANGED_WEAPON_KEY, false);
    }

    /**
     * Checks if the object is pickupable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is pickupable or false
     */
    private boolean isPickupable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + PICKUPABLE_KEY, true); // TODO Si la clave no existe, entonces quiere dicer que si se puede agarrar (como por ejemplo la "Manzana Roja")
    }

    /**
     * Checks if the object is droppable.
     * <p>
     * La mayoria de los objetos son droppables, por lo tanto no es necesario especificarlos en {@code objects.dat} con
     * {droppable=1}. Esto significa que solo se especifican los objetos que no son droppables con {droppable=0}.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is droppable or false
     */
    private boolean isDroppable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + DROPPABLE_KEY, true);
    }

    /**
     * Checks if the object is open.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is open or false
     */
    private boolean isOpen(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + OPEN_KEY, true);
    }

    /**
     * Checks if the object is locked.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is locked or false
     */
    private boolean isLocked(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + LOCKED_KEY, false);
    }

    /**
     * Checks if the object is respawnable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is respawnable or false
     */
    private boolean isRespawnable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RESPAWNABLE_KEY, true);
    }

    /**
     * Legacy potion types. Y are separate object types nowadays.
     * <p>
     * TODO Separate
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
         * @param value value corresponding to the potion type (should be unique)
         */
        PotionType(int value) {
            this.value = value;
        }

        /**
         * Gets the PotionType corresponding to the given integer value.
         *
         * @param value integer value representing a PotionType
         * @return the PotionType associated with the given value, or null if no match is found
         */
        public static PotionType valueOf(int value) {
            for (PotionType type : PotionType.values())
                if (type.value == value) return type;
            return null;
        }

    }

}
