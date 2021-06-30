package org.jalvarova.currency.service;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.repository.CurrencyCodeNamesRepository;
import org.jalvarova.currency.repository.entity.CurrencyCodeNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyCodeNamesService implements ICurrencyCodeNamesService {

    @Autowired
    private CurrencyCodeNamesRepository codeNamesRepository;
    
    @Override
    @Cacheable("names")
    public List<CurrencyCodeNames> findAll() {
        log.info("Find all currency code names");
        return Observable
                .fromIterable(codeNamesRepository.findAll())
                .subscribeOn(Schedulers.io())
                .toList()
                .blockingGet();
    }
}
