package org.bcp.challenge.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtResponse implements Serializable {

    private String jwtToken;

    public JwtResponse jwt(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }
}
