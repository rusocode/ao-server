package com.ao.model.character;

public enum NpcType {

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

    NpcType(int id) {
        this.id = id;
    }

    public static NpcType findById(int id) {
        for (NpcType type : values())
            if (type.id == id) return type;
        return null;
    }

}
