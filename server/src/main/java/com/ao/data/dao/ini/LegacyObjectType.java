package com.ao.data.dao.ini;

import com.ao.model.object.ObjectType;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * World Object Type enumeration, as it was known in the old days of Visual Basic.
 * <p>
 * TODO Rename
 * TODO Unir con ObjectType
 */

public enum LegacyObjectType {

    // NONE(0),
    USE_ONCE(1, ObjectType.FOOD), // TODO Why USE_ONCE and not FOOD?
    WEAPON(2, ObjectType.RANGED_WEAPON, ObjectType.MAGICAL_WEAPON, ObjectType.MELEE_WEAPON),
    ARMOR(3, ObjectType.ARMOR),
    TREE(4, ObjectType.TREE),
    MONEY(5, ObjectType.MONEY),
    DOOR(6, ObjectType.DOOR),
    CONTAINER(7, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    SIGN(8, ObjectType.SIGN),
    KEY(9, ObjectType.KEY),
    FORUM(10, ObjectType.FORUM),
    POTION(11, ObjectType.DEXTERITY_POTION, ObjectType.DEATH_POTION, ObjectType.HP_POTION, ObjectType.MANA_POTION, ObjectType.POISON_POTION, ObjectType.STRENGTH_POTION),
    BOOK(12, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    DRINK(13, ObjectType.DRINK),
    WOOD(14, ObjectType.WOOD),
    // FIXME Bonfire do have special behavior for resting, should be mapped to something else than props
    BONFIRE(15, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    SHIELD(16, ObjectType.SHIELD),
    HELMET(17, ObjectType.HELMET),
    RING(18, ObjectType.ACCESSORY),
    TELEPORT(19, ObjectType.TELEPORT),
    FURNITURE(20, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    JEWELRY(21, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    MINE(22, ObjectType.MINE),
    MINERAL(23, ObjectType.MINERAL),
    PARCHMENT(24, ObjectType.PARCHMENT),
    MUSICAL_INSTRUMENT(26, ObjectType.MUSICAL_INSTRUMENT),
    ANVIL(27, ObjectType.ANVIL),
    FORGE(28, ObjectType.FORGE),
    GEMS(29, ObjectType.PROP, ObjectType.GRABABLE_PROP, ObjectType.INGOT),
    FLOWERS(30, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    BOAT(31, ObjectType.BOAT),
    ARROW(32, ObjectType.AMMUNITION),
    EMPTY_BOTTLE(33, ObjectType.EMPTY_BOTTLE),
    FILLED_BOTTLE(34, ObjectType.FILLED_BOTTLE),
    STAIN(35, ObjectType.PROP, ObjectType.GRABABLE_PROP),
    ELVEN_TREE(36, ObjectType.TREE),
    BACKPACK(37, ObjectType.BACKPACK);

    /** Static maps for O(1) searches. */
    private static final Map<Integer, LegacyObjectType> ID;
    private static final Map<ObjectType, LegacyObjectType> TYPE;

    static {
        // TODO Podria usar Stream.of()? // return Stream.of(values).filter(ot -> ot.value() == value).findFirst().get();
        ID = Arrays.stream(values()).collect(Collectors.toMap(LegacyObjectType::getId, type -> type));

        TYPE = Arrays.stream(values())
                .flatMap(legacyType -> legacyType.mappedTypes.stream().map(objectType -> Map.entry(objectType, legacyType)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));
    }

    private final int id;
    private final Set<ObjectType> mappedTypes;

    LegacyObjectType(int id, ObjectType... mappedTypes) {
        this.id = id;
        this.mappedTypes = EnumSet.copyOf(Arrays.asList(mappedTypes));
    }

    /**
     * Finds a LegacyWorldObjectType based on its unique identifier.
     *
     * @param id a unique identifier of the desired LegacyWorldObjectType
     * @return the corresponding LegacyWorldObjectType if found, or null if no such type exists
     */
    public static LegacyObjectType findById(int id) {
        return ID.get(id);
    }

    /**
     * Finds a LegacyWorldObjectType corresponding to the provided WorldObjectType.
     *
     * @param objectType the WorldObjectType to look for
     * @return the associated LegacyWorldObjectType if found, or null if no match exists
     */
    public static LegacyObjectType findByType(ObjectType objectType) {
        return TYPE.get(objectType);
    }

    public int getId() {
        return id;
    }

    public ObjectType getObjectType() {
        return mappedTypes.size() == 1 ? mappedTypes.iterator().next() : null;
    }

    public Set<ObjectType> getMappedTypes() {
        return mappedTypes;
    }

}