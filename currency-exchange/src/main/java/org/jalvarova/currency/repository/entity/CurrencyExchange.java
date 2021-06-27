package org.jalvarova.currency.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currency_exchange")
@Entity
public class CurrencyExchange {

    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "amount_exchange_rate", precision = 11, scale = 5)
    private BigDecimal amountExchangeRate;

    @Column(name = "currency_exchange_origin")
    private String currencyExchangeOrigin;

    @Column(name = "currency_exchange_destination")
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
