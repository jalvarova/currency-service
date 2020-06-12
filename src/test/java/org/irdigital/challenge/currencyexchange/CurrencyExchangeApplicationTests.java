package org.irdigital.challenge.currencyexchange;

import org.irdigital.challenge.currencyexchange.repository.CurrencyExchangeRepository;
import org.irdigital.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.AssertionErrors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class CurrencyExchangeApplicationTests {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @BeforeEach
    public void loadTest() {
        List<CurrencyExchange> currencyExchangeList = new ArrayList<>();
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
        currencyExchangeRepository.saveAll(currencyExchangeList);
    }

    @Test
    public void testFindAllCurrencyExchange() {
        List<CurrencyExchange> currencyExchanges = (List<CurrencyExchange>) currencyExchangeRepository.findAll();
        AssertionErrors.assertNotNull("Not null list", currencyExchanges);
        AssertionErrors.assertEquals("Size equals currency list ", 6, currencyExchanges.size());
    }


    @Test
    public void testFindByCurrencyExchange() {
        BigDecimal currencyExchangeValue = BigDecimal.valueOf(0.294475d);
        CurrencyExchange currencyExchanges = currencyExchangeRepository.findByCurrencyExchangeOriginAndCurrencyExchangeDestination("PEN", "USD");
        Assertions.assertNotNull(currencyExchanges);
        Assertions.assertEquals(currencyExchangeValue, currencyExchanges.getAmountExchangeRate());
    }

    @Test
    public void testUpdateCurrencyExchange() {
        BigDecimal currencyExchangeValue = BigDecimal.valueOf(0.29446d);
        CurrencyExchange currencyExchanges = currencyExchangeRepository.findByCurrencyExchangeOriginAndCurrencyExchangeDestination("PEN", "USD");
        currencyExchanges.setAmountExchangeRate(currencyExchangeValue);
        CurrencyExchange currencyExchangeSave = currencyExchangeRepository.save(currencyExchanges);

        Assertions.assertNotNull(currencyExchanges);
        Assertions.assertNotNull(currencyExchangeSave);
        Assertions.assertEquals(currencyExchangeValue, currencyExchangeSave.getAmountExchangeRate());
    }

    @AfterEach
    public void remove() {
        currencyExchangeRepository.deleteAll();
        List<CurrencyExchange> currencyExchanges = (List<CurrencyExchange>) currencyExchangeRepository.findAll();
        System.out.println(currencyExchanges.toString());
    }
}
