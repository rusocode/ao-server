package com.ao.model.character;

import java.util.HashMap;
import java.util.Map;

public enum Race {

    HUMAN(1),
    ELF(2),
    DARK_ELF(3), // TODO It should be called DROW_ELF
    GNOME(4),
    DWARF(5);

    private static final Map<Integer, Race> BY_ID = new HashMap<>();

    static {
        for (Race race : values())
            BY_ID.put(race.id, race);
    }

    private final int id;

    Race(int id) {
        this.id = id;
    }

    public static Race findById(int id) {
        return BY_ID.get(id);
    }

}
