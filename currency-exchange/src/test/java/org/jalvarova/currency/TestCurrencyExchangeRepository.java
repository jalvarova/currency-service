package org.jalvarova.currency;

import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.dto.enums.CurrencyCode;
import org.jalvarova.currency.repository.CurrencyExchangeRepository;
import org.jalvarova.currency.repository.entity.CurrencyExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestCurrencyExchangeRepository {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;


    @BeforeEach
    public void init() {
        List<CurrencyExchange> currencyExchanges = new ArrayList<>();

        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
        log.info("Preloading  Currency Names " + currencyExchangeRepository.saveAll(currencyExchanges));
    }

    @Transactional
    @AfterEach
    public void finish() {
        log.info("Delete  Currency Exchange");
        currencyExchangeRepository.deleteAll();
        log.info("List Currency Exchange, {}", currencyExchangeRepository.findAll().size());
    }

    @DisplayName("Test apply currency exchange")
    @Test
    void testApplyCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByApplyCurrency(currencyUSD.name(), currencyPEN.name());
        Assert.notNull(currencyExchange);
        assertThat(currencyExchange.getCurrencyExchangeOrigin()).isEqualTo(currencyUSD.name());
        assertThat(currencyExchange.getCurrencyExchangeDestination()).isEqualTo(currencyPEN.name());
        assertThat(currencyExchange.getAmountExchangeRate()).isEqualTo(BigDecimal.valueOf(3.39608));

    }

    @DisplayName("Test update currency exchange")
    @Test
    void testUpdateCurrencyExchange() {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;

        BigDecimal exchangeRate = BigDecimal.valueOf(4.10);
        BigDecimal exchangeRateOld = BigDecimal.valueOf(3.39608);

        CurrencyExchange currencyExchangeFound = currencyExchangeRepository.findByApplyCurrency(currencyUSD.name(), currencyPEN.name());
        Assert.notNull(currencyExchangeFound);
        assertThat(currencyExchangeFound.getCurrencyExchangeOrigin()).isEqualTo(currencyUSD.name());
        assertThat(currencyExchangeFound.getCurrencyExchangeDestination()).isEqualTo(currencyPEN.name());
        assertThat(currencyExchangeFound.getAmountExchangeRate()).isEqualTo(exchangeRateOld);

        currencyExchangeFound.setAmountExchangeRate(exchangeRate);
        currencyExchangeRepository.save(currencyExchangeFound);

        assertThat(currencyExchangeFound.getCurrencyExchangeOrigin()).isEqualTo(currencyUSD.name());
        assertThat(currencyExchangeFound.getCurrencyExchangeDestination()).isEqualTo(currencyPEN.name());
        assertThat(currencyExchangeFound.getAmountExchangeRate()).isEqualTo(exchangeRate);
    }


    @DisplayName("Test save all currency exchange")
    @Test
    void testSaveCurrencyExchange() {

        CurrencyCode currencyARS = CurrencyCode.ARS;
        CurrencyCode currencyPEN = CurrencyCode.PEN;


        CurrencyExchange exchange = CurrencyExchange
                .builder()
                .amountExchangeRate(BigDecimal.valueOf(12.000))
                .currencyExchangeOrigin(currencyARS.name())
                .currencyExchangeDestination(currencyPEN.name())
                .build();

        currencyExchangeRepository.save(exchange);

        Assert.notNull(exchange);
        assertThat(exchange.getId()).isNotNegative();
        assertThat(exchange.getCurrencyExchangeOrigin()).isEqualTo(currencyARS.name());
        assertThat(exchange.getCurrencyExchangeDestination()).isEqualTo(currencyPEN.name());
        assertThat(exchange.getAmountExchangeRate()).isEqualTo(BigDecimal.valueOf(12.000));

    }

    @DisplayName("Test get all currency exchange")
    @Test
    void testGetAllCurrencyExchange() {
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        CurrencyCode currencyJPY = CurrencyCode.JPY;

        List<CurrencyExchange> exchanges = currencyExchangeRepository.findAll();

        assertThat(exchanges).isNotEmpty();
        assertThat(exchanges.size()).isEqualTo(6);
        assertThat(exchanges.get(5).getCurrencyExchangeDestination()).isEqualTo(currencyJPY.name());
        assertThat(exchanges.get(5).getCurrencyExchangeOrigin()).isEqualTo(currencyPEN.name());
        assertThat(exchanges.get(5).getAmountExchangeRate()).isEqualTo(BigDecimal.valueOf(0.0316777));

    }
}
