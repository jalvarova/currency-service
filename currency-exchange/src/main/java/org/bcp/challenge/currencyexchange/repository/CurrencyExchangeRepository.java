package org.bcp.challenge.currencyexchange.repository;

import org.bcp.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    CurrencyExchange findByCurrencyExchangeOriginAndCurrencyExchangeDestination(String exchangeOrigin, String exchangeDestination);
}
