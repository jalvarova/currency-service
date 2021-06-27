package org.jalvarova.currency.configuration;

import org.jalvarova.currency.repository.CurrencyCodeNamesRepository;
import org.jalvarova.currency.repository.CurrencyExchangeRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        CurrencyExchangeRepository.class,
        CurrencyCodeNamesRepository.class
})
@Import({
        WebSecurityConfig.class,
        DataSourceConfig.class
})
@EnableCaching
public class ApplicationConfiguration {
}
