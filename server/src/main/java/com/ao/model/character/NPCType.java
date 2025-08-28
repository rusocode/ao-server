package com.ao.model.character;

public enum NPCType {

    MERCHANT,
    COMMON,
    RESUCITATOR,
    ROYAL_GUARD,
    TRAINER,
    BANKER,
    NOBLE,
    GAMBLER,
    CHAOS_GUARD,
    NEWBIE_RESUCITATOR,
    GOVERNOR,
    DRAGON;

    private static final NPCType[] values = NPCType.values();

    public static NPCType get(byte index) {
        return values[index];
    }

}
