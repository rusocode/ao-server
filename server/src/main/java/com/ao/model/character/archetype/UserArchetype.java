package com.ao.model.character.archetype;

import com.ao.ioc.ArchetypeLocator;

/**
 * User Archetype enumerator. Wraps archetype classes in an enum.
 */

public enum UserArchetype {
    MAGE(MageArchetype.class),
    CLERIC(ClericArchetype.class),
    WARRIOR(WarriorArchetype.class),
    ASSASIN(AssasinArchetype.class),
    THIEF(ThiefArchetype.class),
    BARD(BardArchetype.class),
    DRUID(DruidArchetype.class),
    BANDIT(BanditArchetype.class),
    PALADIN(PaladinArchetype.class),
    HUNTER(HunterArchetype.class),
    PIRATE(PirateArchetype.class),
    WORKER(WorkerArchetype.class);
    // These ones are not used in 0.13.x and later, but left in in case someone want's them ^_^
//	FISHER(ArchetypeLocator.getArchetype(FisherArchetype.class)),
//	BLACKSMITH(ArchetypeLocator.getArchetype(BlacksmithArchetype.class)),
//	LUMBERJACK(ArchetypeLocator.getArchetype(LumberjackArchetype.class)),
//	MINER(ArchetypeLocator.getArchetype(MinerArchetype.class)),
//	CARPENTER(ArchetypeLocator.getArchetype(CarpenterArchetype.class)),


    private static final UserArchetype[] values = UserArchetype.values();
    private final Class<? extends Archetype> archetypeClass;
    private Archetype archetype; // Lazy init

    UserArchetype(Class<? extends Archetype> archetypeClass) {
        this.archetypeClass = archetypeClass;
    }

    /**
     * Retrieves the UserArchetype for the given index.
     *
     * @param index UserArchetype index
     * @return the UserArchetype
     */
    public static UserArchetype get(byte index) {
        return values[index];
    }

    /**
     * Allows retrieving the enum back from the Archetype.
     *
     * @param archetype archetype to look up in the enum
     * @return The UserArchetype matching the given Archetype, null if none matches
     */
    public static UserArchetype valueOf(Archetype archetype) {
        String archetypeClassName = archetype.getClass().getSimpleName();
        for (UserArchetype arch : values)
            if (arch.getArchetype().getClass().getSimpleName().equals(archetypeClassName))
                return arch;
        return null;
    }

    /**
     * He gets the associated archetype, initializing it only when used.
     *
     * @return The Archetype related to this UserArchetype
     */
    public Archetype getArchetype() {
        if (archetype == null) archetype = ArchetypeLocator.getArchetype(archetypeClass);
        return archetype;
    }

}
