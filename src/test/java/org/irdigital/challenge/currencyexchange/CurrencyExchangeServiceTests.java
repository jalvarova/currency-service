//package org.irdigital.challenge.currencyexchange;
//
//import org.irdigital.challenge.currencyexchange.configuration.AppConfig;
//import org.irdigital.challenge.currencyexchange.configuration.DataSourceConfig;
//import org.irdigital.challenge.currencyexchange.configuration.WebSecurityConfig;
//import org.irdigital.challenge.currencyexchange.dto.CurrencyExchangeDto;
//import org.irdigital.challenge.currencyexchange.repository.CurrencyExchangeRepository;
//import org.irdigital.challenge.currencyexchange.repository.UserRepository;
//import org.irdigital.challenge.currencyexchange.repository.entity.CurrencyExchange;
//import org.irdigital.challenge.currencyexchange.service.CurrencyExchangeServiceImpl;
//import org.irdigital.challenge.currencyexchange.service.ICurrencyExchangeService;
//import org.irdigital.challenge.currencyexchange.service.JwtUserDetailsService;
//import org.irdigital.challenge.currencyexchange.util.JwtAuthenticationEntryPoint;
//import org.irdigital.challenge.currencyexchange.util.JwtAuthorizationFilter;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {
//        AppConfig.class,
//        DataSourceConfig.class,
//        AppConfig.class,
//        JwtAuthenticationEntryPoint.class,
//        UserDetailsService.class,
//        JwtAuthorizationFilter.class,
//        JwtUserDetailsService.class,
//        WebSecurityConfig.class,
//
//})
//@SpringBootTest
//public class CurrencyExchangeServiceTests {
//
//    @Autowired
//    private CurrencyExchangeServiceImpl currencyExchangeService;
//
//    @Mock
//    private CurrencyExchangeRepository currencyExchangeRepository;
//
//    private static final List<CurrencyExchange> currencyExchangeList = new ArrayList<>();
//
//    static {
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
//        currencyExchangeList.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
//    }
//
//
//    @TestConfiguration
//    static class CurrencyExchangeServiceTestContextConfiguration {
//
//        @Bean
//        public ICurrencyExchangeService exchangeService() {
//            return new CurrencyExchangeServiceImpl();
//        }
//    }
//
//    @Test
//    public void testFindAllCurrencyExchange() {
//        Mockito.when(currencyExchangeRepository.findAll()).thenReturn(currencyExchangeList);
//        List<CurrencyExchangeDto> currencyExchangeDtos = currencyExchangeService.getAllCurrencyExchange().blockingGet();
//        Assertions.assertNotNull(currencyExchangeDtos);
//        Assertions.assertEquals(6, currencyExchangeDtos.size());
//    }
//
//
//    @Test
//    public void testFindByCurrencyExchange() {
//    }
//
//    @Test
//    public void testUpdateCurrencyExchange() {
//    }
//
//}
