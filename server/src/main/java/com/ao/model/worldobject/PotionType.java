package com.ao.model.worldobject;

/**
 * Legacy potion types. Y are separate object types nowadays.
 */

public enum PotionType {

    DEXTERITY(1),
    STRENGTH(2),
    HP(3),
    MANA(4),
    POISON(5),
    DEATH(6);

    private final int id;

    PotionType(int id) {
        this.id = id;
    }

    public static PotionType valueOf(int id) {
        for (PotionType potionType : values())
            if (potionType.id == id) return potionType;
        return null;
    }

}