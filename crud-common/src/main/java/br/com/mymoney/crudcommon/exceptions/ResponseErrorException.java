package br.com.mymoney.crudcommon.exceptions;

import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import lombok.Getter;

import java.util.Set;

@Getter
public class ResponseErrorException extends RuntimeException {
    private Set<ResponseErrorDto> errors;

    public ResponseErrorException(Set<ResponseErrorDto> errors) {
        this.errors = errors;
    }
}
