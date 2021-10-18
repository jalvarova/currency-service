package org.bcp.challenge.util;

import org.bcp.challenge.exceptions.CurrencyExchangeNotFound;
import org.bcp.challenge.repository.entity.CurrencyCodeNames;
import org.bcp.challenge.repository.entity.CurrencyExchange;

import java.util.List;

public final class CurrencyUtil {

    public static String findCurrencyCode(List<CurrencyCodeNames> entities, String currencyCode) {
        return entities
                .stream()
                .filter(currencyCodeNames -> currencyCodeNames.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .map(CurrencyCodeNames::getCurrencyName)
                .orElse("");
    }

    public static CurrencyExchange validateNullCurrency(CurrencyExchange currencyExchange) {
        if (currencyExchange.getId() == 0) {
            throw new CurrencyExchangeNotFound("001");
        }
        return currencyExchange;
    }

    public static List<CurrencyExchange> validateNullCollection(List<CurrencyExchange> currencyExchangeList) {
        if (currencyExchangeList.size() == 0) {
            throw new CurrencyExchangeNotFound("002");
        }
        return currencyExchangeList;
    }

}
