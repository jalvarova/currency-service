package org.jalvarova.currency;

import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.enums.CurrencyCode;
import org.jalvarova.currency.repository.CurrencyExchangeRepository;
import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.jalvarova.currency.service.CurrencyExchangeService;
import org.jalvarova.currency.service.ICurrencyCodeNamesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@ExtendWith(SpringExtension.class)
class CurrencyExchangeApplicationTests {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Mock
    private ICurrencyCodeNamesService codeNamesService;

    @BeforeAll
    public static void init() {
    }

    @DisplayName("Test apply currency exchange")
    @Test
    void testApplyCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        BigDecimal exchangeRate = BigDecimal.valueOf(3.39608);
        BigDecimal amount = BigDecimal.valueOf(5);

        Mockito
                .when(currencyExchangeRepository.findByApplyCurrency(currencyUSD.name(), currencyPEN.name()))
                .thenReturn(CurrencyExchange
                        .builder()
                        .id(1L)
                        .amountExchangeRate(exchangeRate)
                        .currencyExchangeOrigin(currencyUSD.name())
                        .currencyExchangeDestination(currencyPEN.name())
                        .build());

        CurrencyExchangeDto currencyExchangeDto =
                CurrencyExchangeDto
                        .builder()
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .amount(amount)
                        .build();

        currencyExchangeService
                .applyExchangeRate(currencyExchangeDto)
                .map(currencyExchangeRsDto -> {
                    log.info("Response {}", currencyExchangeRsDto);
                    return currencyExchangeRsDto;
                })
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getExchangeRateAmount().doubleValue() == 16.98040)
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getCurrencyOrigin().equals(currencyUSD))
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getCurrencyDestination().equals(currencyPEN))
                .assertValue(currencyExchangeRsDto -> Objects.equals(currencyExchangeRsDto.getExchangeRate(), exchangeRate))
                .assertValue(currencyExchangeRsDto -> Objects.equals(currencyExchangeRsDto.getAmount(), amount));
    }

    @DisplayName("Test update currency exchange")
    @Test
    void testUpdateCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        BigDecimal exchangeRate = BigDecimal.valueOf(4.10);
        BigDecimal exchangeRateOld = BigDecimal.valueOf(3.39608);

        CurrencyExchange foundCurrencyExchange = CurrencyExchange
                .builder()
                .id(1L)
                .amountExchangeRate(exchangeRateOld)
                .currencyExchangeOrigin(currencyUSD.name())
                .currencyExchangeDestination(currencyPEN.name())
                .build();

        Mockito
                .when(currencyExchangeRepository.findByApplyCurrency(currencyUSD.name(), currencyPEN.name()))
                .thenReturn(foundCurrencyExchange);

        foundCurrencyExchange.setAmountExchangeRate(exchangeRate);

        Mockito
                .when(currencyExchangeRepository.save(foundCurrencyExchange))
                .thenReturn(foundCurrencyExchange);

        CurrencyExchangeDto currencyExchangeDto =
                CurrencyExchangeDto
                        .builder()
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .amount(exchangeRate)
                        .build();

        currencyExchangeService
                .updateExchangeRate(currencyExchangeDto)
                .map(currencyExchangeRsDto -> {
                    log.info("Response {}", currencyExchangeRsDto);
                    return currencyExchangeRsDto;
                })
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getAmount().doubleValue() == 4.10)
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getCurrencyOrigin().equals(currencyUSD))
                .assertValue(currencyExchangeRsDto -> currencyExchangeRsDto.getCurrencyDestination().equals(currencyPEN));
    }


    @DisplayName("Test save all currency exchange")
    @Test
    void testSaveCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        CurrencyCode currencyEUR = CurrencyCode.EUR;

        CurrencyExchange currencyExchangeUSD = CurrencyExchange
                .builder()
                .id(1L)
                .amountExchangeRate(BigDecimal.valueOf(3.39608))
                .currencyExchangeOrigin(currencyUSD.name())
                .currencyExchangeDestination(currencyPEN.name())
                .build();

        Mockito
                .when(currencyExchangeRepository.findByApplyCurrency(currencyUSD.name(), currencyPEN.name()))
                .thenReturn(currencyExchangeUSD);

        currencyExchangeUSD.setAmountExchangeRate(BigDecimal.valueOf(4.10));

        Mockito
                .when(currencyExchangeRepository.save(currencyExchangeUSD))
                .thenReturn(currencyExchangeUSD);


        CurrencyExchange currencyExchangeEUR = CurrencyExchange
                .builder()
                .id(2L)
                .amountExchangeRate(BigDecimal.valueOf(4.50))
                .currencyExchangeOrigin(currencyEUR.name())
                .currencyExchangeDestination(currencyPEN.name())
                .build();

        Mockito
                .when(currencyExchangeRepository.findByApplyCurrency(currencyEUR.name(), currencyPEN.name()))
                .thenReturn(currencyExchangeEUR);

        currencyExchangeEUR.setAmountExchangeRate(BigDecimal.valueOf(4.50));

        Mockito
                .when(currencyExchangeRepository.save(currencyExchangeEUR))
                .thenReturn(currencyExchangeEUR);

        List<CurrencyExchangeDto> exchanges = Arrays.asList(
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(4.10))
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(4.50))
                        .currencyOrigin(currencyEUR)
                        .currencyDestination(currencyPEN)
                        .build());

        currencyExchangeService
                .saveExchangeRate(exchanges)
                .map(currencyExchangeRsDto -> {
                    log.info("Response {}", currencyExchangeRsDto);
                    return currencyExchangeRsDto;
                })
                .toList()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(x -> x.size() == 2)
                .assertValue(x -> x.get(0).getAmount().doubleValue() == 4.10)
                .assertValue(x -> x.get(0).getCurrencyOrigin().equals(currencyUSD))
                .assertValue(x -> x.get(0).getCurrencyDestination().equals(currencyPEN))
                .assertValue(x -> x.get(1).getAmount().doubleValue() == 4.50)
                .assertValue(x -> x.get(1).getCurrencyOrigin().equals(currencyEUR))
                .assertValue(x -> x.get(1).getCurrencyDestination().equals(currencyPEN));
    }

    @DisplayName("Test get all currency exchange")
    @Test
    void testGetAllCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        CurrencyCode currencyEUR = CurrencyCode.EUR;

        List<CurrencyExchange> exchanges = Arrays.asList(
                CurrencyExchange
                        .builder()
                        .id(1L)
                        .amountExchangeRate(BigDecimal.valueOf(3.39608))
                        .currencyExchangeOrigin(currencyUSD.name())
                        .currencyExchangeDestination(currencyPEN.name())
                        .build(),
                CurrencyExchange
                        .builder()
                        .id(2L)
                        .amountExchangeRate(BigDecimal.valueOf(0.294475))
                        .currencyExchangeOrigin(currencyPEN.name())
                        .currencyExchangeDestination(currencyUSD.name())
                        .build(),
                CurrencyExchange
                        .builder()
                        .id(3L)
                        .amountExchangeRate(BigDecimal.valueOf(3.72884))
                        .currencyExchangeOrigin(currencyEUR.name())
                        .currencyExchangeDestination(currencyPEN.name())
                        .build(),
                CurrencyExchange
                        .builder()
                        .id(4L)
                        .amountExchangeRate(BigDecimal.valueOf(0.268180))
                        .currencyExchangeOrigin(currencyPEN.name())
                        .currencyExchangeDestination(currencyEUR.name())
                        .build());

        Mockito
                .when(currencyExchangeRepository.findAll())
                .thenReturn(exchanges);


        currencyExchangeService
                .getAllCurrencyExchange()
                .map(currencyExchangeRsDto -> {
                    log.info("Response {}", currencyExchangeRsDto);
                    return currencyExchangeRsDto;
                })
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(x -> x.size() == 4)
                .assertValue(x -> x.get(0).getAmount().doubleValue() == 3.39608)
                .assertValue(x -> x.get(3).getCurrencyDestination().equals(currencyEUR));

    }
}
