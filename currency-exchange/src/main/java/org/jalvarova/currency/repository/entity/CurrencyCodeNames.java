package org.jalvarova.currency.repository.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currency_code_names")
@Entity
public class CurrencyCodeNames {

    @GeneratedValue
    @Id
    private Long id;

    @Size(min = 1, max = 4)
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "state")
    private Boolean state;
}
