package com.ao.model.character;

public enum Gender {

    FEMALE,
    MALE;

    private static final Gender[] values = Gender.values();

    public static Gender get(byte index) {
        return values[index];
    }

}
