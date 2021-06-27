package org.jalvarova.currency.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHandler {

    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
