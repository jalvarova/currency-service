package org.bcp.challenge.configuration;

import lombok.extern.slf4j.Slf4j;
import org.bcp.challenge.repository.CurrencyCodeNamesRepository;
import org.bcp.challenge.repository.CurrencyExchangeRepository;
import org.bcp.challenge.repository.UserRepository;
import org.bcp.challenge.repository.entity.CurrencyCodeNames;
import org.bcp.challenge.repository.entity.CurrencyExchange;
import org.bcp.challenge.repository.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
class LoadDataBase {

    private static final List<CurrencyExchange> CURRENCY_EXCHANGES = new ArrayList<>();

    private static final List<CurrencyCodeNames> CURRENCY_CODE_NAMES = new ArrayList<>();

    static {

        CURRENCY_CODE_NAMES.add(CurrencyCodeNames
                .builder()
                .currencyCode("USD")
                .currencyName("Dólar estadounidense")
                .state(Boolean.TRUE)
                .build());

        CURRENCY_CODE_NAMES.add(CurrencyCodeNames
                .builder()
                .currencyCode("PEN")
                .currencyName("Nuevo sol")
                .state(Boolean.TRUE)
                .build());

        CURRENCY_CODE_NAMES.add(CurrencyCodeNames
                .builder()
                .currencyCode("JPY")
                .currencyName("Yen japonés")
                .state(Boolean.TRUE)
                .build());

        CURRENCY_CODE_NAMES.add(CurrencyCodeNames
                .builder()
                .currencyCode("EUR")
                .currencyName("Euro")
                .state(Boolean.TRUE)
                .build());

        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
        CURRENCY_EXCHANGES.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
    }

    @Bean
    CommandLineRunner initDatabase(CurrencyCodeNamesRepository codeNamesRepository, CurrencyExchangeRepository repository, UserRepository userRepository) {
        return args -> {
            log.info("Preloading  Currency Names " + codeNamesRepository.saveAll(CURRENCY_CODE_NAMES));
            log.info("Preloading  Currency Exchange " + repository.saveAll(CURRENCY_EXCHANGES));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            log.info("User Preloading" + userRepository.save(new User("walavo", encoder.encode("12334"), true)));
        };
    }
}
