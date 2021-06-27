package org.jalvarova.currency.mapper;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;
import org.jalvarova.currency.dto.enums.CurrencyCode;
import org.jalvarova.currency.repository.entity.CurrencyCodeNames;
import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.jalvarova.currency.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.List;


@FunctionalInterface
public interface CurrencyMapper {

    void hello();

    Function<CurrencyExchange, CurrencyExchangeDto> toApi = (CurrencyExchange entity) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    BiFunction<CurrencyExchange, List<CurrencyCodeNames>, CurrencyExchangeDto> toApiList = (CurrencyExchange entity, List<CurrencyCodeNames> entities) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .nameOrigin(CurrencyUtil.findCurrencyCode(entities, entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .nameDestination(CurrencyUtil.findCurrencyCode(entities, entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    Function<CurrencyExchange, CurrencyExchangeDto> toApiFunc = (CurrencyExchange entity) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    BiFunction<CurrencyExchange, BigDecimal, CurrencyExchangeRsDto> toApiApply = (CurrencyExchange entity, BigDecimal amount) ->
            CurrencyExchangeRsDto
                    .builder()
                    .amount(amount)
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .exchangeRate(entity.getAmountExchangeRate())
                    .exchangeRateAmount(entity.getAmountExchangeRate().multiply(amount).setScale(5, BigDecimal.ROUND_HALF_UP))
                    .build();


    BiFunction<CurrencyExchange, BigDecimal, CurrencyExchange> toUpdateAmount = (CurrencyExchange entity, BigDecimal amount) -> {
        entity.setAmountExchangeRate(amount);
        return entity;
    };

}
