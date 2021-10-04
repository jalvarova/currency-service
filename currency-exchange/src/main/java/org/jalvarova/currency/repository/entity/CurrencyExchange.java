package org.jalvarova.currency.repository.entity;

import lombok.*;

import java.math.BigDecimal;


/**
 * Agregar anotaciones necesarias para deifnir la clase como Entidad
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Colar aquí
 */
public class CurrencyExchange {

    /**
     * Denfinir el campo como primery key y con valor autogenerado
     */
    private Long id;

    /**
     * Definir el número con una precisión de 11 y con decimal de 5 (Usar JPA)
     */
    private BigDecimal amountExchangeRate;

    private String currencyExchangeOrigin;

    private String currencyExchangeDestination;

    public static CurrencyExchange instanceEmpty() {
        return CurrencyExchange
                .builder()
                .id(0L)
                .build();
    }

    public CurrencyExchange(BigDecimal amountExchangeRate, String currencyExchangeOrigin, String currencyExchangeDestination) {
        this.amountExchangeRate = amountExchangeRate;
        this.currencyExchangeOrigin = currencyExchangeOrigin;
        this.currencyExchangeDestination = currencyExchangeDestination;
    }
}
