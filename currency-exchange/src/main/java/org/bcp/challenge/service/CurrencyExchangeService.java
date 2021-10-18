package org.bcp.challenge.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.bcp.challenge.dto.CurrencyExchangeDto;
import org.bcp.challenge.dto.CurrencyExchangeRsDto;
import org.bcp.challenge.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;

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

