package br.com.mymoney.cadastrationservice.exceptions;

import br.com.mymoney.cadastrationservice.models.dtos.ResponseErrorDto;

import java.util.Set;

public class ResponseErrorException extends RuntimeException {
    private Set<ResponseErrorDto> errors;

    public ResponseErrorException(Set<ResponseErrorDto> errors) {
        this.errors = errors;
    }
}
