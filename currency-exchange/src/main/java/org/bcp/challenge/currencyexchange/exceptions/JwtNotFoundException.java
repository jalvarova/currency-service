package org.bcp.challenge.currencyexchange.exceptions;

public class JwtNotFoundException extends RuntimeException {

    public JwtNotFoundException(String message) {
        super("Not found user" + message);
    }

}
