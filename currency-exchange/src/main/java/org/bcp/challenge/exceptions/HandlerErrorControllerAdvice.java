package org.bcp.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CurrencyExchangeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorHandler> notFoundHandler(CurrencyExchangeNotFound ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Not Found");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.NOT_FOUND.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorHandler);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Bad Request");
        errorHandler.setMessage(ex.getBindingResult().getFieldErrors().stream().map(fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" campo").concat(fieldError.getField())).collect(Collectors.joining(",")));
        errorHandler.setStatus(HttpStatus.BAD_REQUEST.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorHandler);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Bad Request");
        errorHandler.setMessage(ex.getCause().getMessage());
        errorHandler.setStatus(HttpStatus.BAD_REQUEST.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorHandler);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Unsupported Media Type");
        errorHandler.setMessage(ex.getCause().getMessage());
        errorHandler.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorHandler);
    }


    @ResponseBody
    @ExceptionHandler({JwtNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorHandler> noAuthorized(JwtNotFoundException ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Unauthorized");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorHandler);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorHandler> badCredentials(BadCredentialsException ex, HttpServletRequest req) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setError("Unauthorized");
        errorHandler.setMessage(ex.getMessage());
        errorHandler.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorHandler.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(new Date()));
        errorHandler.setPath(req.getServletPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorHandler);
    }
}
