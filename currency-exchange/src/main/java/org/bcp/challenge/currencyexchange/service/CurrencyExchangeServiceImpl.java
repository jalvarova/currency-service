package org.bcp.challenge.currencyexchange.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.bcp.challenge.currencyexchange.dto.CurrencyExchangeDto;
import org.bcp.challenge.currencyexchange.dto.CurrencyExchangeRsDto;
import org.bcp.challenge.currencyexchange.exceptions.CurrencyExchangeNotFound;
import org.bcp.challenge.currencyexchange.mapper.CurrencyExchangeMapper;
import org.bcp.challenge.currencyexchange.repository.CurrencyExchangeRepository;
import org.bcp.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyExchangeServiceImpl implements ICurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private CurrencyExchangeMapper currencyExchangeMapper;

    @Override
    public Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByCurrencyExchangeOriginAndCurrencyExchangeDestination(currencyOrigin, currencyDestination);
        if (currencyExchange == null) {
            throw new CurrencyExchangeNotFound("001");
        }
        return Single.just(currencyExchange)
                .map(entity -> currencyExchangeMapper.toDto(entity, dto.getAmount()));
    }

    @Override
    public Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByCurrencyExchangeOriginAndCurrencyExchangeDestination(currencyOrigin, currencyDestination);
        if (currencyExchange == null) {
            throw new CurrencyExchangeNotFound("001");
        }

        return Single.just(currencyExchange)
                .map(entity -> {
                    entity.setAmountExchangeRate(dto.getAmount());
                    currencyExchangeRepository.save(entity);
                    return entity;
                }).map(currencyExchangeMapper::toDto);
    }

    @Override
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        List<CurrencyExchange> currencyExchangeList = (List<CurrencyExchange>) currencyExchangeRepository.findAll();
        if (currencyExchangeList.size() == 0) {
            throw new CurrencyExchangeNotFound("002");
        }
        return Single.just(currencyExchangeList)
                .flatMapObservable(Observable::fromIterable)
                .map(currencyExchangeMapper::toDto)
                .toList();
    }
}
