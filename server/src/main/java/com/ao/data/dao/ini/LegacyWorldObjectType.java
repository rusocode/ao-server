package com.ao.data.dao.ini;

import com.ao.model.worldobject.WorldObjectType;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * World Object Type enumeration, as it was known in the old days of Visual Basic.
 * <p>
 * TODO Rename
 * TODO Unir con WorldObjectType
 */

public enum LegacyWorldObjectType {

    // NONE(0),
    USE_ONCE(1, WorldObjectType.FOOD), // TODO Why USE_ONCE and not FOOD?
    WEAPON(2, WorldObjectType.RANGED_WEAPON, WorldObjectType.MAGICAL_WEAPON, WorldObjectType.MELEE_WEAPON),
    ARMOR(3, WorldObjectType.ARMOR),
    TREE(4, WorldObjectType.TREE),
    MONEY(5, WorldObjectType.MONEY),
    DOOR(6, WorldObjectType.DOOR),
    CONTAINER(7, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    SIGN(8, WorldObjectType.SIGN),
    KEY(9, WorldObjectType.KEY),
    FORUM(10, WorldObjectType.FORUM),
    POTION(11, WorldObjectType.DEXTERITY_POTION, WorldObjectType.DEATH_POTION, WorldObjectType.HP_POTION, WorldObjectType.MANA_POTION, WorldObjectType.POISON_POTION, WorldObjectType.STRENGTH_POTION),
    BOOK(12, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    DRINK(13, WorldObjectType.DRINK),
    WOOD(14, WorldObjectType.WOOD),
    // FIXME Bonfire do have special behavior for resting, should be mapped to something else than props
    BONFIRE(15, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    SHIELD(16, WorldObjectType.SHIELD),
    HELMET(17, WorldObjectType.HELMET),
    RING(18, WorldObjectType.ACCESSORY),
    TELEPORT(19, WorldObjectType.TELEPORT),
    FURNITURE(20, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    JEWELRY(21, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    MINE(22, WorldObjectType.MINE),
    MINERAL(23, WorldObjectType.MINERAL),
    PARCHMENT(24, WorldObjectType.PARCHMENT),
    MUSICAL_INSTRUMENT(26, WorldObjectType.MUSICAL_INSTRUMENT),
    ANVIL(27, WorldObjectType.ANVIL),
    FORGE(28, WorldObjectType.FORGE),
    GEMS(29, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP, WorldObjectType.INGOT),
    FLOWERS(30, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    BOAT(31, WorldObjectType.BOAT),
    ARROW(32, WorldObjectType.AMMUNITION),
    EMPTY_BOTTLE(33, WorldObjectType.EMPTY_BOTTLE),
    FILLED_BOTTLE(34, WorldObjectType.FILLED_BOTTLE),
    STAIN(35, WorldObjectType.PROP, WorldObjectType.GRABABLE_PROP),
    ELVEN_TREE(36, WorldObjectType.TREE),
    BACKPACK(37, WorldObjectType.BACKPACK);

    /** Static maps for O(1) searches. */
    private static final Map<Integer, LegacyWorldObjectType> ID;
    private static final Map<WorldObjectType, LegacyWorldObjectType> TYPE;

    static {
        // TODO Podria usar Stream.of()? // return Stream.of(values).filter(ot -> ot.value() == value).findFirst().get();
        ID = Arrays.stream(values()).collect(Collectors.toMap(LegacyWorldObjectType::getId, type -> type));

        TYPE = Arrays.stream(values())
                .flatMap(legacyType -> legacyType.mappedTypes.stream().map(objectType -> Map.entry(objectType, legacyType)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));
    }

    private final int id;
    private final Set<WorldObjectType> mappedTypes;

    LegacyWorldObjectType(int id, WorldObjectType... mappedTypes) {
        this.id = id;
        this.mappedTypes = EnumSet.copyOf(Arrays.asList(mappedTypes));
    }

    /**
     * Finds and returns a LegacyWorldObjectType based on its unique identifier.
     *
     * @param id unique identifier of the desired LegacyWorldObjectType
     * @return the corresponding LegacyWorldObjectType if found, or null if no such type exists
     */
    public static LegacyWorldObjectType findById(int id) {
        return ID.get(id);
    }

    /**
     * Finds and returns a LegacyWorldObjectType corresponding to the provided WorldObjectType.
     *
     * @param objectType the WorldObjectType to look for
     * @return the associated LegacyWorldObjectType if found, or null if no match exists
     */
    public static LegacyWorldObjectType findByType(WorldObjectType objectType) {
        return TYPE.get(objectType);
    }

    public int getId() {
        return id;
    }

    public WorldObjectType getObjectType() {
        return mappedTypes.size() == 1 ? mappedTypes.iterator().next() : null;
    }

    public Set<WorldObjectType> getMappedTypes() {
        return mappedTypes;
    }

}