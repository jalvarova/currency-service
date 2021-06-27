package org.jalvarova.currency.repository;

import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    @Query(value = "FROM CurrencyExchange e WHERE" +
            " e.currencyExchangeOrigin = :exchangeOrigin AND" +
            " e.currencyExchangeDestination = :exchangeDestination")
    CurrencyExchange findByApplyCurrency(String exchangeOrigin, String exchangeDestination);

    @Query("FROM CurrencyExchange cn")
    List<CurrencyExchange> findAll();
}
