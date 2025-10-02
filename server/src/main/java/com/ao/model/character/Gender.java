package com.ao.model.character;

import java.util.HashMap;
import java.util.Map;

public enum Gender {

    MALE(1),
    FEMALE(2);

    private static final Map<Integer, Gender> BY_ID = new HashMap<>();

    static {
        for (Gender gender : values())
            BY_ID.put(gender.id, gender);
    }

    private final int id;

    Gender(int id) {
        this.id = id;
    }

    public static Gender findById(int id) {
        return BY_ID.get(id);
    }

}
