package org.bcp.challenge.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.bcp.challenge.currencyexchange.dto.enums.CurrencyCode;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"amount", "exchangeRateAmount", "exchangeRate", "currencyOrigin", "currencyDestination"})
public class CurrencyExchangeRsDto {

    private BigDecimal amount;

    private BigDecimal exchangeRateAmount;

    private BigDecimal exchangeRate;

    private CurrencyCode currencyOrigin;

    private CurrencyCode currencyDestination;

}
