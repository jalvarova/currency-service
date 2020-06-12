package org.irdigital.challenge.currencyexchange.configuration;

import lombok.extern.slf4j.Slf4j;
import org.irdigital.challenge.currencyexchange.repository.CurrencyExchangeRepository;
import org.irdigital.challenge.currencyexchange.repository.UserRepository;
import org.irdigital.challenge.currencyexchange.repository.entity.CurrencyExchange;
import org.irdigital.challenge.currencyexchange.repository.entity.User;
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

    static List<CurrencyExchange> currencyExchanges = new ArrayList<>();

    static {
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
    }

    @Bean
    CommandLineRunner initDatabase(CurrencyExchangeRepository repository, UserRepository userRepository) {
        return args -> {
            log.info("Preloading " + repository.saveAll(currencyExchanges));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            log.info("User Preloading" + userRepository.save(new User("walavo", encoder.encode("12334"), true)));
        };
    }
}
