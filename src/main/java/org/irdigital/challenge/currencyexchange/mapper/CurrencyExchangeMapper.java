package org.irdigital.challenge.currencyexchange.mapper;

import org.irdigital.challenge.currencyexchange.dto.CurrencyExchangeDto;
import org.irdigital.challenge.currencyexchange.dto.CurrencyExchangeRsDto;
import org.irdigital.challenge.currencyexchange.dto.enums.CurrencyCode;
import org.irdigital.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyExchangeMapper {

    public CurrencyExchangeRsDto toDto(CurrencyExchange currencyExchange, BigDecimal amount) {
        CurrencyExchangeRsDto response = new CurrencyExchangeRsDto();
        response.setAmount(amount);
        response.setCurrencyDestination(CurrencyCode.valueOf(currencyExchange.getCurrencyExchangeDestination()));
        response.setCurrencyOrigin(CurrencyCode.valueOf(currencyExchange.getCurrencyExchangeOrigin()));
        response.setExchangeRate(currencyExchange.getAmountExchangeRate());
        response.setExchangeRateAmount(currencyExchange.getAmountExchangeRate().multiply(amount).setScale(5, BigDecimal.ROUND_HALF_UP));
        return response;
    }

    public CurrencyExchangeDto toDto(CurrencyExchange entity) {
        CurrencyExchangeDto response = new CurrencyExchangeDto();
        response.setAmount(entity.getAmountExchangeRate());
        response.setCurrencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()));
        response.setCurrencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()));
        return response;
    }

    public CurrencyExchange toEntity(CurrencyExchangeDto dto) {
        CurrencyExchange entity = new CurrencyExchange();
        entity.setAmountExchangeRate(dto.getAmount());
        entity.setCurrencyExchangeDestination(dto.getCurrencyDestination().name());
        entity.setCurrencyExchangeOrigin(dto.getCurrencyOrigin().name());
        return entity;
    }
}
