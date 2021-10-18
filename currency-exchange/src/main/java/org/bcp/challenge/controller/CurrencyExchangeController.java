package org.bcp.challenge.controller;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.bcp.challenge.dto.CurrencyExchangeDto;
import org.bcp.challenge.dto.CurrencyExchangeRsDto;
import org.bcp.challenge.service.ICurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Agregar anotaciones necesarias para habilitar un REST API
 */
@Validated
@RequestMapping("api/v1")
public class CurrencyExchangeController {

    @Autowired
    private ICurrencyExchangeService currencyExchangeService;

    /**
     * Crear un endpoint de tipo Post con el path /currency-exchange/apply y con el content type json
     */
    public Single<CurrencyExchangeRsDto> getCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.applyExchangeRate(request);
    }

    /**
     * Crear un endpoint de tipo Put con el path /currency-exchange y con el content type json
     */
    public Single<CurrencyExchangeDto> updateCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.updateExchangeRate(request);
    }

    /**
     * Crear un endpoint de tipo Get con el path /currency-exchange y con el content type json
     */
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return currencyExchangeService.getAllCurrencyExchange();
    }


    /**
     * Crear un endpoint de tipo Post con el path /currency-exchange y con el content type json
     */
    public Observable<CurrencyExchangeDto> saveCurrencyExchange(@RequestBody @Valid List<CurrencyExchangeDto> request) {
        return currencyExchangeService.saveExchangeRate(request);
    }

}
