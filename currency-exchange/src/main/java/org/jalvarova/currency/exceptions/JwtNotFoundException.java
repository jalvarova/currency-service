package org.jalvarova.currency.exceptions;

public class JwtNotFoundException extends RuntimeException {

    public JwtNotFoundException() {
        super();
    }

    public JwtNotFoundException(String message) {
        super("Not found user" + message);
    }

}
