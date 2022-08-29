package br.com.mymoney.cadastrationservice.controllers;

import br.com.mymoney.cadastrationservice.exceptions.ResponseErrorException;
import br.com.mymoney.cadastrationservice.exceptions.ValidationException;
import br.com.mymoney.cadastrationservice.models.dtos.ResponseErrorDto;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.logging.Level;

@Log
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseErrorException.class)
    public ResponseEntity<Object> handleResponseErrorException(ResponseErrorException exception, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(exception.getErrors());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(new ResponseErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception, WebRequest webRequest) {
        log.log(Level.FINE, exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body("Ocorreu um erro não previsto.");//TODO internacionalizar
    }
}
