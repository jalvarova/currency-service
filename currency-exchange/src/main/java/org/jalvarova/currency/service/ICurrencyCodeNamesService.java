package org.jalvarova.currency.service;

import org.jalvarova.currency.repository.entity.CurrencyCodeNames;

import java.util.List;

public interface ICurrencyCodeNamesService {

    List<CurrencyCodeNames> findAll();
}
