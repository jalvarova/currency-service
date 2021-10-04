package org.jalvarova.currency.repository;

import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    /**
     * Crear con @Query una función correcta de base de datos que con un Origen y destino
     * pueda obtener el tió de cambio
     */
    CurrencyExchange findByApplyCurrency(String exchangeOrigin, String exchangeDestination);

    /**
     * Crear con @Query una función correcta de base de datos 
     * pueda obtener todo el listado de tipo de cambio
     */
    List<CurrencyExchange> findAll();

}
