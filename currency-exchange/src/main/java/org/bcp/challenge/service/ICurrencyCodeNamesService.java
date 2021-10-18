package org.bcp.challenge.service;

import org.bcp.challenge.repository.entity.CurrencyCodeNames;

import java.util.List;

public interface ICurrencyCodeNamesService {

    List<CurrencyCodeNames> findAll();
}
