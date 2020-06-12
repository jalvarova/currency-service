package org.irdigital.challenge.currencyexchange.configuration;

import org.irdigital.challenge.currencyexchange.repository.CurrencyExchangeRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = CurrencyExchangeRepository.class)
@Import({DataSourceConfig.class, WebSecurityConfig.class})
public class AppConfig {
}
