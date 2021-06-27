package org.jalvarova.currency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jalvarova.currency.dto.enums.CurrencyCode;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyExchangeDto {

    private CurrencyCode currencyOrigin;

    private String nameOrigin;

    private CurrencyCode currencyDestination;

    private String nameDestination;

    @DecimalMin(value = "0.1", inclusive = false)
    private BigDecimal amount;
}
