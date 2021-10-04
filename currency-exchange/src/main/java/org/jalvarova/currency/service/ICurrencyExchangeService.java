package org.jalvarova.currency.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;

import java.util.List;

public interface ICurrencyExchangeService {

    /**
     * Implementar una lógica que con el currencyOrigin y currencyDestination se pueda obtener el tipo de cambio
     * del monto ingresado.
     */
    Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto);

    /**
     * Implementar una lógica que actualice el monto de tipo de cambio.
     */
    Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto);

    /**
     * Implementar una lógica que liste todos los tipos de cambio.
     */
    Single<List<CurrencyExchangeDto>> getAllCurrencyExchange();

    /**
     * Implementar una lógica que cree o actualice tipos de cambios que 
     * ingresen en la lista.
     */
    Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos);

}
