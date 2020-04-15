package org.bcp.challenge.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtRequest implements Serializable {

    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    private String password;
}
