package br.com.mymoney.crudcommon.controllers;

import br.com.mymoney.crudcommon.exceptions.ResponseErrorException;
import br.com.mymoney.crudcommon.exceptions.ValidationException;
import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "br.com.mymoney")
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

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
        return ResponseEntity.internalServerError().body(messageSource.getMessage("error.unknow", null, null));
    }
}
