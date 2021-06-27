package org.jalvarova.currency.exceptions;

public class CurrencyExchangeNotFound extends RuntimeException {

    public CurrencyExchangeNotFound(String message) {
        super("Not found currency exchange " + message);
    }

}
