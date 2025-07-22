package com.ao.service.login;

import java.io.Serial;

public class LoginErrorException extends Exception {

    @Serial
    private static final long serialVersionUID = -6248141276568605517L;

    public LoginErrorException(String message) {
        super(message);
    }

}