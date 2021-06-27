package org.jalvarova.currency.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;

import java.util.List;

public interface ICurrencyExchangeService {

    Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto);

    Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto);

    Single<List<CurrencyExchangeDto>> getAllCurrencyExchange();

    Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos);

}
