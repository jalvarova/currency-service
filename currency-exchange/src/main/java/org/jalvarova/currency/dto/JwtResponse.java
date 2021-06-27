package org.jalvarova.currency.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonPropertyOrder(value = {"tokenType", "token", "refresh"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtResponse implements Serializable {

    private String token;
    private String tokenType;
    private String refresh;

    public JwtResponse jwt(String jwtToken) {
        this.token = jwtToken;
        this.refresh = jwtToken;
        return this;
    }

    public JwtResponse tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
}
