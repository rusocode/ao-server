package com.ao.model.character;

public enum NPCType {

    COMMON(0),
    RESUCITATOR(1),
    ROYAL_GUARD(2),
    TRAINER(3),
    BANKER(4),
    NOBLE(5),
    DRAGON(6),
    GAMBLER(7),
    CHAOS_GUARD(8),
    NEWBIE_RESUCITATOR(9),
    PRETORIAN(10),
    GOVERNOR(11),
    MERCHANT(12);

    private final int id;

    NPCType(int id) {
        this.id = id;
    }

    public static NPCType findById(int id) {
        for (NPCType type : values())
            if (type.id == id) return type;
        return null;
    }

}
