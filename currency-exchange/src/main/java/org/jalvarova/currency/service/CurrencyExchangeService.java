package org.jalvarova.currency.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;
import org.jalvarova.currency.repository.CurrencyExchangeRepository;
import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.jalvarova.currency.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.jalvarova.currency.mapper.CurrencyMapper.*;

@Service
@Slf4j
public class CurrencyExchangeService implements ICurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private ICurrencyCodeNamesService codeNamesService;

    @Override
    public Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        return Single.fromCallable(() -> currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination))
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(CurrencyExchange.instanceEmpty())
                .map(CurrencyUtil::validateNullCurrency)
                .map(x -> toApiApply.apply(x, dto.getAmount()));
    }

    @Override
    public Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        return Single.fromCallable(() -> currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination))
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(CurrencyExchange.instanceEmpty())
                .map(CurrencyUtil::validateNullCurrency)
                .map(x -> toUpdateAmount.apply(x, dto.getAmount()))
                .map(currencyExchangeRepository::save)
                .map(toApi);
    }

    @Override
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return Single.just(currencyExchangeRepository.findAll())
                .subscribeOn(Schedulers.io())
                .map(CurrencyUtil::validateNullCollection)
                .flatMapObservable(Observable::fromIterable)
                .map(currencyExchange -> toApiList.apply(currencyExchange, codeNamesService.findAll()))
                .toList();
    }

    @Override
    public Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos) {

        return Observable
                .fromIterable(dtos)
                .map(this::saveAll)
                .map(toApiFunc);
    }


    private CurrencyExchange saveAll(CurrencyExchangeDto dtos) {

        String currencyOrigin = dtos.getCurrencyOrigin().name();
        String currencyDestination = dtos.getCurrencyDestination().name();

        CurrencyExchange currencyExchange = new CurrencyExchange();

        CurrencyExchange foundCurrencyExchange = currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination);
        if (Objects.nonNull(foundCurrencyExchange)) {
            foundCurrencyExchange.setAmountExchangeRate(dtos.getAmount());
            currencyExchange = currencyExchangeRepository.save(foundCurrencyExchange);
        } else {
            currencyExchange.setCurrencyExchangeOrigin(currencyOrigin);
            currencyExchange.setCurrencyExchangeDestination(currencyDestination);
            currencyExchange.setAmountExchangeRate(dtos.getAmount());
            currencyExchange = currencyExchangeRepository.save(currencyExchange);
        }
        return currencyExchange;
    }

}

