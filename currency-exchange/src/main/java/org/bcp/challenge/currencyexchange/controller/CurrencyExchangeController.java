package org.bcp.challenge.currencyexchange.controller;

import io.reactivex.Single;
import org.bcp.challenge.currencyexchange.dto.CurrencyExchangeDto;
import org.bcp.challenge.currencyexchange.dto.CurrencyExchangeRsDto;
import org.bcp.challenge.currencyexchange.service.ICurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1")
public class CurrencyExchangeController {

    @Autowired
    private ICurrencyExchangeService currencyExchangeService;

    @PostMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyExchangeRsDto> getCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.applyExchangeRate(request);
    }


    @PutMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyExchangeDto> updateCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.updateExchangeRate(request);
    }

    @GetMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return currencyExchangeService.getAllCurrencyExchange();
    }
}
