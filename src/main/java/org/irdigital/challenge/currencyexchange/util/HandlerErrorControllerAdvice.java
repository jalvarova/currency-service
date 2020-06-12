package org.irdigital.challenge.currencyexchange.util;

import org.irdigital.challenge.currencyexchange.exceptions.CurrencyExchangeNotFound;
import org.irdigital.challenge.currencyexchange.exceptions.ErrorHandler;
import org.irdigital.challenge.currencyexchange.exceptions.JwtNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CurrencyExchangeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorHandler> notFoundHandler(CurrencyExchangeNotFound ex) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Not Found");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.NOT_FOUND.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        //errorHandler.setPath(request.getURI().getPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorHandler);
    }

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status, WebRequest reques) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Bad Request");
        errorHandler.setMessage(ex.getBindingResult().getFieldErrors().stream().map(fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" campo").concat(fieldError.getField())).collect(Collectors.joining(",")));
        errorHandler.setStatus(HttpStatus.BAD_REQUEST.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        //errorHandler.setPath(request.getURI().getPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorHandler);
    }

    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status, WebRequest reques) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Bad Request");
        errorHandler.setMessage(ex.getCause().getMessage());
        errorHandler.setStatus(HttpStatus.BAD_REQUEST.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        //errorHandler.setPath(request.getURI().getPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorHandler);
    }

//    @Override
//    @ResponseBody
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status, WebRequest reques) {
//        ErrorHandler errorHandler = new ErrorHandler();
//        errorHandler.setError("Unsupported Media Type");
//        errorHandler.setMessage(ex.getCause().getMessage());
//        errorHandler.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
//        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
//        //errorHandler.setPath(request.getURI().getPath());
//        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorHandler);
//    }


    @ResponseBody
    @ExceptionHandler({JwtNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorHandler> noAuthorized(JwtNotFoundException ex) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Unauthorized");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        //errorHandler.setPath(request.getURI().getPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorHandler);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorHandler> BadCredentials(BadCredentialsException ex) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Unauthorized");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        //errorHandler.setPath(request.getURI().getPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorHandler);
    }
}
