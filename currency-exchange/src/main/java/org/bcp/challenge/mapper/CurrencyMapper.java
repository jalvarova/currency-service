package org.bcp.challenge.mapper;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import org.bcp.challenge.dto.CurrencyExchangeDto;
import org.bcp.challenge.dto.CurrencyExchangeRsDto;
import org.bcp.challenge.dto.enums.CurrencyCode;
import org.bcp.challenge.repository.entity.CurrencyCodeNames;
import org.bcp.challenge.repository.entity.CurrencyExchange;
import org.bcp.challenge.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.List;


@FunctionalInterface
public interface CurrencyMapper {

    void demo();

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
