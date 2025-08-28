package com.ao.model.character;

public enum Race {

    DARK_ELF,
    DWARF,
    ELF,
    GNOME,
    HUMAN;

    private static final Race[] values = Race.values();

    public static Race get(byte index) {
        return values[index];
    }

}
