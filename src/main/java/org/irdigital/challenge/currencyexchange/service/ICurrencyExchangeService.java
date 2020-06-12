package org.irdigital.challenge.currencyexchange.service;

import io.reactivex.Single;
import org.irdigital.challenge.currencyexchange.dto.CurrencyExchangeDto;
import org.irdigital.challenge.currencyexchange.dto.CurrencyExchangeRsDto;

import java.util.List;

public interface ICurrencyExchangeService {

    Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto);

    Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto);

    Single<List<CurrencyExchangeDto>> getAllCurrencyExchange();

}
