package org.irdigital.challenge.currencyexchange.repository;

import org.irdigital.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    CurrencyExchange findByCurrencyExchangeOriginAndCurrencyExchangeDestination(String exchangeOrigin, String exchangeDestination);
}
