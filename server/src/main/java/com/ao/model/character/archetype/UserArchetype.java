package com.ao.model.character.archetype;

import com.ao.ioc.ArchetypeLocator;

import java.util.HashMap;
import java.util.Map;

/**
 * User Archetype enumerator. Wraps archetype classes in an enum.
 */

public enum UserArchetype {
    MAGE(1, ArchetypeLocator.getArchetype(MageArchetype.class)),
    CLERIC(2, ArchetypeLocator.getArchetype(ClericArchetype.class)),
    WARRIOR(3, ArchetypeLocator.getArchetype(WarriorArchetype.class)),
    ASSASIN(4, ArchetypeLocator.getArchetype(AssasinArchetype.class)),
    THIEF(5, ArchetypeLocator.getArchetype(ThiefArchetype.class)),
    BARD(6, ArchetypeLocator.getArchetype(BardArchetype.class)),
    DRUID(7, ArchetypeLocator.getArchetype(DruidArchetype.class)),
    BANDIT(8, ArchetypeLocator.getArchetype(BanditArchetype.class)),
    PALADIN(9, ArchetypeLocator.getArchetype(PaladinArchetype.class)),
    HUNTER(10, ArchetypeLocator.getArchetype(HunterArchetype.class)),
    WORKER(11, ArchetypeLocator.getArchetype(WorkerArchetype.class)),
    PIRATE(12, ArchetypeLocator.getArchetype(PirateArchetype.class));
    // These are not used in 0.13.x and later, but left in case someone wants them ^_^
//	FISHER(ArchetypeLocator.getArchetype(FisherArchetype.class)),
//	BLACKSMITH(ArchetypeLocator.getArchetype(BlacksmithArchetype.class)),
//	LUMBERJACK(ArchetypeLocator.getArchetype(LumberjackArchetype.class)),
//	MINER(ArchetypeLocator.getArchetype(MinerArchetype.class)),
//	CARPENTER(ArchetypeLocator.getArchetype(CarpenterArchetype.class)),

    private static final Map<Archetype, UserArchetype> BY_ARCHETYPE = new HashMap<>();
    private static final Map<Integer, UserArchetype> BY_ID = new HashMap<>();

    static {
        for (UserArchetype userArchetype : values()) {
            BY_ARCHETYPE.put(userArchetype.archetype, userArchetype);
            BY_ID.put(userArchetype.id, userArchetype);
        }
    }

    private final int id;
    private final Archetype archetype;

    UserArchetype(int id, Archetype archetype) {
        this.id = id;
        this.archetype = archetype;
    }

    public static UserArchetype findById(int id) {
        return BY_ID.get(id);
    }

    /**
     * Allows retrieving the enum back from the Archetype.
     *
     * @param archetype archetype to look up in the enum
     * @return the UserArchetype matching the given Archetype, null if none matches
     */
    public static UserArchetype findByArchetype(Archetype archetype) {
        // First, try direct lookup (for performance when same instance)
        UserArchetype result = BY_ARCHETYPE.get(archetype);
        if (result != null) return result;

        // Fallback: compare by class type
        for (UserArchetype userArchetype : values())
            if (userArchetype.archetype.getClass().equals(archetype.getClass())) return userArchetype;

        return null;
    }

    /**
     * Gets the Archetype related to this UserArchetype.
     *
     * @return the Archetype related to this UserArchetype
     */
    public Archetype getArchetype() {
        return archetype;
    }

    public int getId() {
    	return id;
    }

}
