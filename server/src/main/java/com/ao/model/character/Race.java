package com.ao.model.character;

public enum Race {

    DARK_ELF,
    DWARF,
    ELF,
    GNOME,
    HUMAN;

    public static Race get(byte index) {
        return Race.values()[index];
    }

}
