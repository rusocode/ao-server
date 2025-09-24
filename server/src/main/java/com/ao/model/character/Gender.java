package com.ao.model.character;

public enum Gender {

    FEMALE,
    MALE;

    public static Gender get(byte index) {
        return Gender.values()[index];
    }

}
