package org.bcp.challenge.configuration;

import org.bcp.challenge.repository.CurrencyCodeNamesRepository;
import org.bcp.challenge.repository.CurrencyExchangeRepository;
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
