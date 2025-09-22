package com.ao.data.dao.ini;

import com.ao.data.dao.ObjectDAO;
import com.ao.data.dao.exception.DAOException;
import com.ao.model.character.Race;
import com.ao.model.character.archetype.UserArchetype;
import com.ao.model.object.PotionType;
import com.ao.model.object.ResourceSourceType;
import com.ao.model.object.WoodType;
import com.ao.model.object.ObjectType;
import com.ao.model.object.properties.*;
import com.ao.model.object.properties.crafting.Craftable;
import com.ao.model.object.properties.crafting.CraftingSkill;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Implementation of the Object DAO backed by INI files.
 * <p>
 * En las claves de tipo flag (0 o 1, true o false), la claves obvias como por ejemplo {@code droppable=1} (que se puede tirar) no
 * se especifican en {@code objects.dat} ya que seria redundante especificar que la mayoria de objetos si se pueden tirar al
 * suelo, por lo tanto esta clave obtiene el valor "true" por defecto desde el metodo {@code isDroppable()} si la clave no se
 * especifico en el objeto.
 * <p>
 * TODO Se podrian reemplazar los archivos INI por JSON
 * TODO Si dividieramos el archivo objects.dat en varios archivos especificos de cada objeto, entonces no seria necesario especificar
 * la clave "object_type" para cada objeto
 */

public class ObjectDAOIni implements ObjectDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectDAOIni.class);

    private static final String INIT_HEADER = "INIT";
    private static final String OBJECT_COUNT_KEY = "object_count";

    /** Ini file keys. */
    private static final String NAME_KEY = "name";
    private static final String GRAPHIC_INDEX_KEY = "graphic_index";
    private static final String OBJECT_TYPE_KEY = "object_type";
    private static final String VALUE_KEY = "value";
    private static final String SMITHING_SKILL_KEY = "smithing_skill"; // SkHerreria
    private static final String NAVIGATION_SKILL_KEY = "navigation_skill"; // Nueva clave, antes se usaba "MinSkill" (inconsistencia)
    private static final String CARPENTRY_SKILL_KEY = "carpentry_skill"; // SkCarpinteria
    private static final String NEWBIE_KEY = "newbie";
    private static final String MIN_ARMOR_DEFENSE_KEY = "min_armor_defense";
    private static final String MAX_ARMOR_DEFENSE_KEY = "max_armor_defense";
    private static final String MIN_MAGIC_DEFENSE_KEY = "min_magic_defense";
    private static final String MAX_MAGIC_DEFENSE_KEY = "max_magic_defense";
    private static final String DWARF_KEY = "dwarf"; // TODO Deberia llamarse FORBIDDEN_DWARF_KEY o FORBIDDEN_RACE_KEY
    private static final String DARK_ELF_KEY = "dark_elf";
    private static final String ELF_KEY = "elf";
    private static final String GNOME_KEY = "gnome";
    private static final String HUMAN_KEY = "human";
    private static final String FORBIDDEN_ARCHETYPE_KEY = "forbidden_archetype"; // CP (clase prohibida)
    private static final String MIN_HIT_KEY = "min_hit";
    private static final String MAX_HIT_KEY = "max_hit";
    private static final String STABBING_KEY = "stabbing"; // Apuñala
    private static final String PIERCING_DAMAGE_KEY = "piercing_damage"; // Refuerzo
    private static final String MAGIC_POWER_KEY = "magic_power"; // Antes llamada "StaffPower" que tambien se usaba para determinar si el objeto era magico (inconsistencia)
    private static final String MAGICAL_WEAPON_KEY = "magical_weapon"; // Nueva clave, antes se usaba "StaffPower" (inconsistencia)
    private static final String RANGED_WEAPON_KEY = "ranged_weapon"; // Proyectil
    /**
     * Puede que sea redundante especificar si el objeto arco tiene municiones, pero esta flag diferencia dos casos dentro de las
     * armas a distancia: armas que consumen municion externa (p. ej., arcos que usan flechas) y armas arrojadizas/autosuficientes
     * (p. ej., cuchillas).
     */
    private static final String AMMO_KEY = "ammo";
    private static final String STAFF_DAMAGE_BONUS_KEY = "staff_damage_bonus";
    private static final String HUNGER_POINTS_KEY = "hunger_points"; // MinHam
    private static final String THIRST_POINTS_KEY = "thirst_points"; // MinAgu
    private static final String RANGE_KEY = "range"; // Radio
    private static final String POTION_TYPE_KEY = "potion_type";
    private static final String MIN_MODIFIER = "min_modifier";
    private static final String MAX_MODIFIER = "max_modifier";
    private static final String EFFECT_DURATION_KEY = "effect_duration";
    private static final String EQUIPPED_ARMOR_GRAPHIC_KEY = "equipped_armor_graphic"; // NumRopaje
    private static final String EQUIPPED_WEAPON_GRAPHIC_KEY = "equipped_weapon_graphic"; // Anim
    private static final String SPELL_INDEX_KEY = "spell_index";
    private static final String MINERAL_INDEX_KEY = "mineral_index";
    private static final String KEY_KEY = "key";
    private static final String OPEN_KEY = "open";
    private static final String LOCKED_KEY = "locked";
    private static final String OPEN_INDEX_KEY = "open_index";
    private static final String CLOSED_INDEX_KEY = "closed_index";
    private static final String PICKUPABLE_KEY = "pickupable"; // Agarrable
    private static final String RESPAWNABLE_KEY = "respawnable"; // Crucial
    private static final String DROPPABLE_KEY = "droppable"; // NoSeCae
    private static final String NO_LOG_KEY = "no_log";
    private static final String BIG_GRAPHIC_KEY = "big_graphic"; // VGrande TODO En algunos proyectos de AO, esta propiedad suele nombrarse como "GrhSecundario", pero no entiendo para que es
    private static final String TEXT_KEY = "text";
    private static final String FORUM_NAME_KEY = "forum_name"; // ID
    private static final String BACKPACK_TYPE_KEY = "backpack_type";
    private static final String INGOT_INDEX_KEY = "ingot_index";
    private static final String WOOD_KEY = "wood";
    private static final String ELVEN_WOOD_KEY = "elven_wood";
    private static final String GOLD_INGOT_KEY = "gold_ingot";
    private static final String SILVER_INGOT_KEY = "silver_ingot";
    private static final String IRON_INGOT_KEY = "iron_ingot";

    /** Prefixs keys. */
    private static final String OBJECT_PREFIX = "OBJ";
    private static final String SOUND_PREFIX = "sound";

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

    private Map<Integer, Craftable> craftables;
    private ObjectProperties[] objectProperties;

    @Inject
    public ObjectDAOIni(@Named("objectsFilePath") String objectsFilePath, @Named("itemsPerRow") int itemsPerRow) {
        this.objectsFilePath = objectsFilePath;
        this.itemsPerRow = itemsPerRow;
    }

    @Override
    public void load() throws DAOException {
        INIConfiguration ini = null;
        // Reset craftables
        craftables = new HashMap<>();
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
        int objectCount = IniUtils.getRequiredInt(ini, INIT_HEADER + "." + OBJECT_COUNT_KEY);

        objectProperties = new ObjectProperties[objectCount];

        for (int i = 1; i <= objectCount; i++)
            objectProperties[i - 1] = loadObject(i, ini);

    }

    @Override
    public Map<Integer, Craftable> getAllCraftables() throws DAOException {
        if (craftables == null) load(); // Force the ini to be loaded!
        return craftables;
    }

    @Override
    public ObjectProperties getObjectProperties(int id) {
        return objectProperties[id - 1];
    }

    /**
     * Loads a object.
     *
     * @param id  object's id
     * @param ini ini configuration
     * @return new object
     */
    private ObjectProperties loadObject(int id, INIConfiguration ini) {

        String section = OBJECT_PREFIX + id;

        String name = IniUtils.getString(ini, section + "." + NAME_KEY, "");
        int graphicIndex = IniUtils.getInt(ini, section + "." + GRAPHIC_INDEX_KEY, -1);
        int objectTypeId = IniUtils.getInt(ini, section + "." + OBJECT_TYPE_KEY, -1);

        LegacyObjectType objectType = LegacyObjectType.findById(objectTypeId);

        if (objectType == null) {
            LOGGER.error("Unknown object type in section [{}]", section);
            return null;
        }

        ObjectProperties object = null;

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

        // Check if the object is craftable
        if (object != null && getCraftingSkill(ini, section) != null) {
            try {
                craftables.put(object.getId(), loadCraftable(object, ini, section));
            } catch (DAOException e) {
                LOGGER.error("Item id '{}' cannot be crafted!", id);
            }
        }

        return object;
    }

    private Craftable loadCraftable(ObjectProperties object, INIConfiguration ini, String section) throws DAOException {
        CraftingSkill craftingSkill = getCraftingSkill(ini, section);
        if (null == craftingSkill) throw new DAOException("This object cannot be crafted!");
        return new Craftable(object, craftingSkill, getCraftingSkillPoints(ini, section), getWood(ini, section), getElvenWood(ini, section), getGoldIngot(ini, section), getSilverIngot(ini, section), getIronIngot(ini, section));
    }

    private CraftingSkill getCraftingSkill(INIConfiguration ini, String section) {
        String carpentrySkill = section + "." + CARPENTRY_SKILL_KEY;
        String smithingSkill = section + "." + SMITHING_SKILL_KEY;
        if (IniUtils.getInt(ini, carpentrySkill, -1) != -1) return CraftingSkill.CARPENTRY;
        if (IniUtils.getInt(ini, smithingSkill, -1) != -1) return CraftingSkill.SMITHING;
        return null;
    }

    private ObjectProperties loadWood(ObjectType type, int id, String name, int graphic, WoodType woodType, INIConfiguration ini, String section) {
        return new WoodProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), woodType);
    }

    private ObjectProperties loadResourceSource(ObjectType type, int id, String name, int graphic, LegacyObjectType legactType, INIConfiguration ini, String section) {
        ResourceSourceInfo info = getResourceSourceInfo(legactType, ini, section);
        if (info == null) {
            LOGGER.error("Unexpected resource source of type {}", type.name());
            return null;
        }
        return new ResourceSourceProperties(type, id, name, graphic, info.id, info.type);
    }

    private ObjectProperties loadDefensiveItem(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new DefensiveItemProperties(type, id, name, graphic, getValue(ini, section), getCraftingSkillPoints(ini, section), getForbiddenArchetypes(ini, section),
                getForbiddenRaces(ini, section), isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section),
                getEquippedGraphic(ini, section), getMinArmorDefense(ini, section), getMaxArmorDefense(ini, section), getMinMagicDefense(ini, section), getMaxMagicDefense(ini, section));
    }

    private ObjectProperties loadTeleport(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new TeleportProperties(type, id, name, graphic, getRange(ini, section));
    }

    private ObjectProperties loadProps(int id, String name, int graphic, INIConfiguration ini, String section) {
        if (!isPickupable(ini, section)) return new ObjectProperties(ObjectType.PROP, id, name, graphic);
        return loadGenericItem(ObjectType.GRABABLE_PROP, id, name, graphic, ini, section);
    }

    private ObjectProperties loadGenericItem(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new ItemProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section));
    }

    private ObjectProperties loadPotion(int id, String name, int graphic, INIConfiguration ini, String section) {

        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);

        PotionType potionType = getPotionType(ini, section);

        if (potionType == PotionType.DEATH)
            return new ItemProperties(ObjectType.DEATH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);
        else if (potionType == PotionType.POISON)
            return new ItemProperties(ObjectType.POISON_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable);

        int minModifier = getMinModifier(ini, section);
        int maxModifier = getMaxModifier(ini, section);

        if (potionType == PotionType.HP)
            return new StatModifyingItemProperties(ObjectType.HP_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier);
        else if (potionType == PotionType.MANA)
            return new StatModifyingItemProperties(ObjectType.MANA_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier);

        int effectDuration = getEffectDuration(ini, section);

        if (potionType == PotionType.STRENGTH)
            return new TemporalStatModifyingItemProperties(ObjectType.STRENGTH_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier, effectDuration);
        else if (potionType == PotionType.DEXTERITY)
            return new TemporalStatModifyingItemProperties(ObjectType.DEXTERITY_POTION, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, minModifier, maxModifier, effectDuration);

        // This should never happen...
        LOGGER.error("Potion type '{}' not found", potionType.name());
        return null;
    }

    private ObjectProperties loadMusicalInstrument(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new MusicalInstrumentProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), getEquippedGraphic(ini, section), getSounds(ini, section));
    }

    private ObjectProperties loadBoat(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new BoatProperties(type, id, name, graphic, getValue(ini, section), getNavigationSkill(ini, section), getCraftingSkillPoints(ini, section),
                getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section), isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section),
                isRespawnable(ini, section), getEquippedGraphic(ini, section), getMinArmorDefense(ini, section), getMaxArmorDefense(ini, section),
                getMinMagicDefense(ini, section), getMaxMagicDefense(ini, section), getMinHit(ini, section), getMaxHit(ini, section));
    }

    private ObjectProperties loadFood(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int hungerPoints = getHungerPoints(ini, section);
        // TODO Por que se pasa "hungerPoints" dos veces?
        return new StatModifyingItemProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), hungerPoints, hungerPoints);
    }

    private ObjectProperties loadDrink(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {

        int value = getValue(ini, section);
        boolean newbie = isNewbie(ini, section);
        int modifier = 0;
        List<UserArchetype> forbiddenArchetypes = getForbiddenArchetypes(ini, section);
        List<Race> forbiddenRaces = getForbiddenRaces(ini, section);
        boolean noLog = getNoLog(ini, section);
        boolean droppable = isDroppable(ini, section);
        boolean respawnable = isRespawnable(ini, section);

        if (type != ObjectType.EMPTY_BOTTLE) getThirstPoints(ini, section); // TODO No se esta usando este valor

        int openIndex = getOpenIndex(ini, section);
        int closedIndex = getClosedIndex(ini, section);

        // Is it refillable?
        if (openIndex != 0 && closedIndex != 0) {

            boolean filled = id == closedIndex;

            RefillableStatModifyingItemProperties otherObjectProperties = null;

            // Only take the other object that's already loaded to make sure the other one has already loaded
            if (filled && id > openIndex)
                otherObjectProperties = (RefillableStatModifyingItemProperties) objectProperties[openIndex - 1];
            else if (!filled && id > closedIndex)
                otherObjectProperties = (RefillableStatModifyingItemProperties) objectProperties[closedIndex - 1];

            return new RefillableStatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, modifier, modifier, filled, otherObjectProperties);
        } else
            return new StatModifyingItemProperties(type, id, name, graphic, value, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, modifier, modifier);

    }

    private ObjectProperties loadWeapon(int id, String name, int graphic, INIConfiguration ini, String section) {
        int value = getValue(ini, section);
        int craftingSkill = getCraftingSkillPoints(ini, section);
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
            return new RangedWeaponProperties(ObjectType.RANGED_WEAPON, id, name, graphic, value, craftingSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit, ammo);
        } else if (isMagicalWeapon(ini, section)) {
            int magicPower = getMagicPower(ini, section);
            int staffDamageBonus = getStaffDamageBonus(ini, section);
            return new StaffProperties(ObjectType.MAGICAL_WEAPON, id, name, graphic, value, craftingSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit, magicPower, staffDamageBonus);
        }
        return new WeaponProperties(ObjectType.MELEE_WEAPON, id, name, graphic, value, craftingSkill, forbiddenArchetypes, forbiddenRaces, newbie, noLog, droppable, respawnable, equippedGraphic, stabbing, piercingDamage, minHit, maxHit);
    }

    private ObjectProperties loadAmmunition(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new AmmunitionProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), getEquippedGraphic(ini, section),
                getMinHit(ini, section), getMaxHit(ini, section));
    }

    private ObjectProperties loadParchment(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        int spellIndex = getSpellIndex(ini, section);
        // TODO Create the Spell implementation
        return new ParchmentProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), null);
    }

    private ObjectProperties loadKey(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new KeyProperties(type, id, name, graphic, getValue(ini, section), getCraftingSkillPoints(ini, section), getForbiddenArchetypes(ini, section),
                getForbiddenRaces(ini, section), isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), getKey(ini, section));
    }

    private ObjectProperties loadDoor(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {

        boolean open = isOpen(ini, section);
        boolean locked = isLocked(ini, section);
        int key = getKey(ini, section);

        int openIndex = getOpenIndex(ini, section);
        int closedIndex = getClosedIndex(ini, section);

        DoorProperties otherObjectProperties = null;

        if (openIndex == 0 || closedIndex == 0) {
            LOGGER.error("Invalid door definition for id '{}'. Ids for open and closed states are required.", id);
            return null;
        }

        // Only take the other object that's already loaded to make sure the other one has already loaded
        if (open && id > closedIndex) otherObjectProperties = (DoorProperties) objectProperties[closedIndex - 1];
        else if (!open && id > openIndex) otherObjectProperties = (DoorProperties) objectProperties[openIndex - 1];

        return new DoorProperties(type, id, name, graphic, open, locked, key, otherObjectProperties);
    }

    private ObjectProperties loadSign(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new SignProperties(type, id, name, graphic, getBigGraphic(ini, section), getText(ini, section));
    }

    private ObjectProperties loadForum(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new ForumProperties(type, id, name, graphic, getForumName(ini, section));
    }

    private ObjectProperties loadBackpack(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new BackpackProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), getEquippedGraphic(ini, section),
                getAmountForBackpackType(getBackpackType(ini, section)));
    }

    private ObjectProperties loadMineral(ObjectType type, int id, String name, int graphic, INIConfiguration ini, String section) {
        return new MineralProperties(type, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section), getForbiddenRaces(ini, section),
                isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section), getIngotIndex(ini, section));
    }

    private ObjectProperties loadIngot(int id, String name, int graphic, INIConfiguration ini, String section) {
        return new ItemProperties(ObjectType.INGOT, id, name, graphic, getValue(ini, section), getForbiddenArchetypes(ini, section),
                getForbiddenRaces(ini, section), isNewbie(ini, section), getNoLog(ini, section), isDroppable(ini, section), isRespawnable(ini, section));
    }

    private ObjectProperties loadGem(int id, String name, int graphic, INIConfiguration ini, String section) {
        for (int ingot : INGOTS)
            if (ingot == id) return loadIngot(id, name, graphic, ini, section);
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
     * @return the list of sounds the object may reproduce or empty list if none exists
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
     * Gets object's crafting skill points.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's crafting skill points, or 0 if the key of the crafting skill is missing or has an invalid value
     */
    private int getCraftingSkillPoints(INIConfiguration ini, String section) {
        String[] craftingSkills = {CARPENTRY_SKILL_KEY, SMITHING_SKILL_KEY};
        for (String craftingSkill : craftingSkills) {
            int value = IniUtils.getInt(ini, section + "." + craftingSkill, -1);
            if (value != -1) return value;
        }
        return 0; // TODO Deberia devolver -1?
    }

    /**
     * Gets object equipped graphic.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object equipped graphic, or 0 if the key of the object equipped graphic is missing or has an invalid value
     */
    private int getEquippedGraphic(INIConfiguration ini, String section) {
        String[] equippedGraphics = {EQUIPPED_ARMOR_GRAPHIC_KEY, EQUIPPED_WEAPON_GRAPHIC_KEY};
        for (String equippedGraphic : equippedGraphics) {
            int value = IniUtils.getInt(ini, section + "." + equippedGraphic, -1);
            if (value != -1) return value;
        }
        return 0;
    }

    /**
     * Gets object's potion type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's potion type, or 0 if the key {@code POTION_TYPE_KEY} is missing or has an invalid value
     */
    private PotionType getPotionType(INIConfiguration ini, String section) {
        return PotionType.valueOf(IniUtils.getInt(ini, section + "." + POTION_TYPE_KEY, 0));
    }

    /**
     * Gets object text.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object text, or 0 if the key {@code TEXT_KEY} is missing or has an invalid value
     */
    private String getText(INIConfiguration ini, String section) {
        return IniUtils.getString(ini, section + "." + TEXT_KEY, "");
    }

    /**
     * Gets object forum name.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object forum name, or 0 if the key {@code FORUM_NAME_KEY} is missing or has an invalid value
     */
    private String getForumName(INIConfiguration ini, String section) {
        return IniUtils.getString(ini, section + "." + FORUM_NAME_KEY, "");
    }

    /**
     * Gets object value.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object value, or 0 if the key {@code VALUE_KEY} is missing or has an invalid value
     */
    private int getValue(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + VALUE_KEY, 0);
    }

    /**
     * Gets object's wood.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's wood, or 0 if the key {@code IRON_INGOT_KEY} is missing or has an invalid value
     */
    private int getWood(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + WOOD_KEY, 0);
    }

    /**
     * Gets object's elven wood.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's elven wood, or 0 if the key {@code ELVEN_WOOD_KEY} is missing or has an invalid value
     */
    private int getElvenWood(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + ELVEN_WOOD_KEY, 0);
    }

    /**
     * Gets object's iron ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's iron ingots, or 0 if the key {@code IRON_INGOT_KEY} is missing or has an invalid value
     */
    private int getIronIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + IRON_INGOT_KEY, 0);
    }

    /**
     * Gets object's silver ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's silver ingots, or 0 if the key {@code SILVER_INGOT_KEY} is missing or has an invalid value
     */
    private int getSilverIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + SILVER_INGOT_KEY, 0);
    }

    /**
     * Gets object's gold ingots.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's gold ingots, or 0 if the key {@code GOLD_INGOT_KEY} is missing or has an invalid value
     */
    private int getGoldIngot(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + GOLD_INGOT_KEY, 0);
    }

    /**
     * Gets object's min armor defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min defense, or 0 if the key {@code MIN_ARMOR_DEFENSE_KEY} is missing or has an invalid value
     */
    private int getMinArmorDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_ARMOR_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's max armor defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max armor defense, or 0 if the key {@code MAX_ARMOR_DEFENSE_KEY} is missing or has an invalid value
     */
    private int getMaxArmorDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_ARMOR_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's min magic defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min magic defense, or 0 if the key {@code MIN_MAGIC_DEFENSE_KEY} is missing or has an invalid value
     */
    private int getMinMagicDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets an object's max magic defense.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max magic defense, or 0 if the key {@code MAX_MAGIC_DEFENSE_KEY} is missing or has an invalid value
     */
    private int getMaxMagicDefense(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_MAGIC_DEFENSE_KEY, 0);
    }

    /**
     * Gets object's min hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min hit, or 0 if the key {@code MIN_HIT_KEY} is missing or has an invalid value
     */
    private int getMinHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_HIT_KEY, 0);
    }

    /**
     * Gets object's max hit.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max hit, or 0 if the key {@code MAX_HIT_KEY} is missing or has an invalid value
     */
    private int getMaxHit(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_HIT_KEY, 0);
    }

    /**
     * Gets object's hunger points.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's hunger points, or 0 if the key {@code HUNGER_POINTS_KEY} is missing or has an invalid value
     */
    private int getHungerPoints(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + HUNGER_POINTS_KEY, 0);
    }

    /**
     * Gets object thirst points.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object thirst points, or 0 if the key {@code THIRST_POINTS_KEY} is missing or has an invalid value
     */
    private int getThirstPoints(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + THIRST_POINTS_KEY, 0);
    }

    /**
     * Gets object's piercing damage.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's piercing damage, or 0 if the key {@code PIERCING_DAMAGE_KEY} is missing or has an invalid value
     */
    private int getPiercingDamage(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + PIERCING_DAMAGE_KEY, 0);
    }

    /**
     * Gets an object's magic power.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's magic power, or 0 if the key {@code MAGIC_POWER_KEY} is missing or has an invalid value
     */
    private int getMagicPower(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAGIC_POWER_KEY, 0);
    }

    /**
     * Gets object's staff damage bonus.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's staff damage bonus, or 0 if the key {@code STAFF_DAMAGE_BONUS_KEY} is missing or has an invalid value
     */
    private int getStaffDamageBonus(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + STAFF_DAMAGE_BONUS_KEY, 0);
    }

    /**
     * Gets object's navigation skill.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's navigation skill, or 0 if the key {@code NAVIGATION_SKILL_KEY} is missing or has an invalid value
     */
    private int getNavigationSkill(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + NAVIGATION_SKILL_KEY, 0);
    }

    /**
     * Gets object's range.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's range, or 0 if the key {@code RANGE_KEY} is missing or has an invalid value
     */
    private int getRange(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + RANGE_KEY, 0);
    }

    /**
     * Gets object's min modifier.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's min modifier, or 0 if the key {@code MIN_MODIFIER} is missing or has an invalid value
     */
    private int getMinModifier(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MIN_MODIFIER, 0);
    }

    /**
     * Gets object's max modifier.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's max modifier, or 0 if the key {@code MAX_MODIFIER} is missing or has an invalid value
     */
    private int getMaxModifier(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MAX_MODIFIER, 0);
    }

    /**
     * Gets object effect duration.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object effect duration, or 0 if the key {@code EFFECT_DURATION_KEY} is missing or has an invalid value
     */
    private int getEffectDuration(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + EFFECT_DURATION_KEY, 0);
    }

    /**
     * Gets object's spell index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's spell index, or 0 if the key {@code SPELL_INDEX_KEY} is missing or has an invalid value
     */
    private int getSpellIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + SPELL_INDEX_KEY, 0);
    }

    /**
     * Gets object's mineral index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's a mineral index, or 0 if the key {@code MINERAL_INDEX_KEY} is missing or has an invalid value
     */
    private int getMineralIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + MINERAL_INDEX_KEY, 0);
    }

    /**
     * Gets object's key.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's key, or 0 if the key {@code KEY_KEY} is missing or has an invalid value
     */
    private int getKey(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + KEY_KEY, 0);
    }

    /**
     * Gets object closed index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object closed index, or 0 if the key {@code CLOSED_INDEX_KEY} is missing or has an invalid value
     */
    private int getClosedIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + CLOSED_INDEX_KEY, 0);
    }

    /**
     * Gets object open index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object open index, or 0 if the key {@code OPEN_INDEX_KEY} is missing or has an invalid value
     */
    private int getOpenIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + OPEN_INDEX_KEY, 0);
    }

    /**
     * Gets object's big graphic.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's big graphic, or 0 if the key {@code BIG_GRAPHIC_KEY} is missing or has an invalid value
     */
    private int getBigGraphic(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + BIG_GRAPHIC_KEY, 0);
    }

    /**
     * Gets object's backpack type.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's backpack type, or 0 if the key {@code BACKPACK_TYPE_KEY} is missing or has an invalid value
     */
    private int getBackpackType(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + BACKPACK_TYPE_KEY, 0);
    }

    /**
     * Gets object's ingot index.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return the object's ingot index, or 0 if the key {@code INGOT_INDEX_KEY} is missing or has an invalid value
     */
    private int getIngotIndex(INIConfiguration ini, String section) {
        return IniUtils.getInt(ini, section + "." + INGOT_INDEX_KEY, 0);
    }

    /**
     * Gets the amount for the given backpack type.
     *
     * @param backpackType backpack type
     * @return the amount for the given backpack type
     */
    private int getAmountForBackpackType(int backpackType) {
        return backpackType * itemsPerRow;
    }

    /**
     * Check if the object should log.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object should log, or false if the key {@code NO_LOG_KEY} is missing or has an invalid value
     */
    private boolean getNoLog(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + NO_LOG_KEY, false);
    }

    /**
     * Checks if the object has ammo.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object has ammo, or false if the key {@code AMMO_KEY} is missing or has an invalid value
     */
    private boolean hasAmmo(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + AMMO_KEY, false);
    }

    /**
     * Checks if the object is newbie.
     * <p>
     * IMPORTANTE: la clave <b>newbie</b> solo se especifica en objects.dat para los objetos newbies con el valor 1, es decir que
     * esta clave es opcional ya que no hace falta especificar newbie=0 para TODOS los otros objetos ya que para las claves
     * faltantes, newbie=0 en este caso, las maneja con un valor false por defecto. Esto se hace para evitar tener que especificar
     * la clave <b>newbie</b> en todos los objetos que no son newbies.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is newbie, or false if the key {@code NEWBIE_KEY} is missing or has an invalid value
     */
    private boolean isNewbie(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + NEWBIE_KEY, false);
    }

    /**
     * Checks if the object is stabbing.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is stabbing, or false if the key {@code STABBING_KEY} is missing or has an invalid value
     */
    private boolean isStabbing(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + STABBING_KEY, false);
    }

    /**
     * Checks if the object is a magical weapon.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is a magical weapon, or false if the key {@code MAGICAL_WEAPON_KEY} is missing or has an invalid
     * value
     */
    private boolean isMagicalWeapon(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + MAGICAL_WEAPON_KEY, false);
    }

    /**
     * Checks if the object is a ranged weapon.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is a ranged weapon, or false if the key {@code RANGED_WEAPON_KEY} is missing or has an invalid
     * value
     */
    private boolean isRangedWeapon(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + RANGED_WEAPON_KEY, false);
    }

    /**
     * Checks if the object is pickupable.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is pickupable; false if the key {@code PICKUPABLE_KEY} is missing or has an invalid value, or is
     * equal to 0
     */
    private boolean isPickupable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + PICKUPABLE_KEY, true);
    }

    /**
     * Checks if the object is droppable.
     * <p>
     * La mayoria de los objetos son droppables, por lo tanto no es necesario especificar {@code droppable = 1} en
     * {@code objects.dat}. Esto significa que solo se especifican los objetos que no son droppables con {@code droppable = 0}.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is droppable; false if the key {@code DROPPABLE_KEY} is missing or has an invalid value, or is
     * equal to 0
     */
    private boolean isDroppable(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + DROPPABLE_KEY, true);
    }

    /**
     * Checks if the object is open.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object is open, or false if the key {@code OPEN_KEY} is missing or has an invalid value
     */
    private boolean isOpen(INIConfiguration ini, String section) {
        return IniUtils.getBoolean(ini, section + "." + OPEN_KEY, false);
    }

    /**
     * Check if the object locked.
     *
     * @param ini     ini configuration
     * @param section section from which to read the value
     * @return true if the object locked, or false if the key {@code LOCKED_KEY} is missing or has an invalid value
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

    private ResourceSourceInfo getResourceSourceInfo(LegacyObjectType type, INIConfiguration ini, String section) {
        return switch (type) {
            case TREE -> new ResourceSourceInfo(WOOD_INDEX, ResourceSourceType.TREE);
            case ELVEN_TREE -> new ResourceSourceInfo(ELVEN_WOOD_INDEX, ResourceSourceType.TREE);
            case MINE -> new ResourceSourceInfo(getMineralIndex(ini, section), ResourceSourceType.MINE);
            default -> null;
        };
    }

    private record ResourceSourceInfo(int id, ResourceSourceType type) {
    }

}
