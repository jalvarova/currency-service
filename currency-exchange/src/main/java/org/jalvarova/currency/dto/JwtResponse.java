package org.jalvarova.currency.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@JsonPropertyOrder(value = {"tokenType", "token", "refresh"})
@Builder
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
