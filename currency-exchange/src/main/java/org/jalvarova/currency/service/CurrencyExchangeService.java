package org.jalvarova.currency.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;
import org.jalvarova.currency.repository.CurrencyExchangeRepository;
import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;
import java.util.Objects;

import static org.jalvarova.currency.mapper.CurrencyMapper.*;

/**
 * Agregar anotaciones necesarias para definir la clase como Service
 */
@Slf4j
public class CurrencyExchangeService implements ICurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private ICurrencyCodeNamesService codeNamesService;


    @Override
    public Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto) {

        return Single.just(null);
    }

    @Override
    public Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto) {
        return Single.just(null);
    }

    @Override
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return Single.just(null);
    }

    @Override
    public Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos) {

        return Observable.fromIterable(Arrays.asList(null));
    }

}

