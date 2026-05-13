package com.ao.data.dao.exception;

import java.io.Serial;

public class CharacterNotFoundException extends DAOException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CharacterNotFoundException(String username) {
        super("Character file not found: " + username);
    }

}
