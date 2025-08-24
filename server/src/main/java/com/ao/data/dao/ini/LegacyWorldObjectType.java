package com.ao.data.dao.ini;

import com.ao.model.worldobject.WorldObjectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * World Object Type enumeration, as it was known in the old days of Visual Basic.
 * <p>
 * TODO Rename
 */

public enum LegacyWorldObjectType {

    USE_ONCE(1, WorldObjectType.FOOD), // TODO Why USE_ONCE and not FOOD?
    WEAPON(2, new HashSet<>(Arrays.asList(WorldObjectType.RANGED_WEAPON, WorldObjectType.MAGICAL_WEAPON, WorldObjectType.MELEE_WEAPON))),
    ARMOR(3, WorldObjectType.ARMOR),
    TREE(4, WorldObjectType.TREE),
    MONEY(5, WorldObjectType.MONEY),
    DOOR(6, WorldObjectType.DOOR),
    CONTAINER(7, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    SIGN(8, WorldObjectType.SIGN),
    KEY(9, WorldObjectType.KEY),
    FORUM(10, WorldObjectType.FORUM),
    POTION(11, new HashSet<>(Arrays.asList(WorldObjectType.DEXTERITY_POTION, WorldObjectType.DEATH_POTION, WorldObjectType.HP_POTION, WorldObjectType.MANA_POTION, WorldObjectType.POISON_POTION, WorldObjectType.STRENGTH_POTION))),
    BOOK(12, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    DRINK(13, WorldObjectType.DRINK),
    WOOD(14, WorldObjectType.WOOD),
    // FIXME Bonfire do have special behavior for resting, should be mapped to something else than props
    BONFIRE(15, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    SHIELD(16, WorldObjectType.SHIELD),
    HELMET(17, WorldObjectType.HELMET),
    RING(18, WorldObjectType.ACCESSORY),
    TELEPORT(19, WorldObjectType.TELEPORT),
    FURNITURE(20, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    JEWELRY(21, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    MINE(22, WorldObjectType.MINE),
    MINERAL(23, WorldObjectType.MINERAL),
    PARCHMENT(24, WorldObjectType.PARCHMENT),
    MUSICAL_INSTRUMENT(26, WorldObjectType.MUSICAL_INSTRUMENT),
    ANVIL(27, WorldObjectType.ANVIL),
    FORGE(28, WorldObjectType.FORGE),
    GEMS(29, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP, WorldObjectType.INGOT))),
    FLOWERS(30, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    BOAT(31, WorldObjectType.BOAT),
    ARROW(32, WorldObjectType.AMMUNITION),
    EMPTY_BOTTLE(33, WorldObjectType.EMPTY_BOTTLE),
    FILLED_BOTTLE(34, WorldObjectType.FILLED_BOTTLE),
    STAIN(35, new HashSet<>(Arrays.asList(WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP))),
    ELVEN_TREE(36, WorldObjectType.TREE),
    BACKPACK(37, WorldObjectType.BACKPACK);

    private final int id;
    private final Set<WorldObjectType> plausibleCurrentTypes;
    private WorldObjectType objectType;

    /**
     * Creates a LegacyWorldObjectType.
     *
     * @param id         object type id
     * @param objectType object type for which to search for a LegacyWorldObjectType
     */
    LegacyWorldObjectType(int id, WorldObjectType objectType) {
        this.id = id;
        this.objectType = objectType;
        plausibleCurrentTypes = new HashSet<>();
        plausibleCurrentTypes.add(objectType);
    }

    /**
     * Creates a LegacyWorldObjectType.
     *
     * @param id             object type id
     * @param plausibleTypes possible object types for this type
     */
    LegacyWorldObjectType(int id, Set<WorldObjectType> plausibleTypes) {
        this.id = id;
        plausibleCurrentTypes = plausibleTypes;
    }

    /**
     * Returns the {@code LegacyWorldObjectType} that matches the given id.
     *
     * @param id object type id
     * @return the matching {@code LegacyWorldObjectType} or {@code null} if none is found
     */
    public static LegacyWorldObjectType findById(int id) {
        for (LegacyWorldObjectType objectType : LegacyWorldObjectType.values())
            if (objectType.id == id) return objectType;
        return null;
    }

    /**
     * Returns the {@code LegacyWorldObjectType} that matches the given {@link WorldObjectType}.
     *
     * @param objectType object type
     * @return the matching {@code LegacyWorldObjectType} or {@code null} if none is found
     * @throws NoSuchElementException if no mapping exists for the given type
     */
    public static LegacyWorldObjectType findByType(WorldObjectType objectType) {
        for (LegacyWorldObjectType objType : LegacyWorldObjectType.values())
            if (objType.plausibleCurrentTypes.contains(objectType)) return objType;
        throw new NoSuchElementException("No mapping found for object type " + objectType);
    }

    public int getId() {
        return id;
    }

    public WorldObjectType getObjectType() {
        return objectType;
    }

    public Set<WorldObjectType> getPlausibleCurrentTypes() {
        return plausibleCurrentTypes;
    }

}