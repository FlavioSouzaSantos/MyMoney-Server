package br.com.mymoney.cadastrationservice.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@NoArgsConstructor
public class ResponseErrorDto {
    private String field;
    private String error;

    public ResponseErrorDto(FieldError fieldError){
        field = fieldError.getField();
        error = fieldError.getDefaultMessage();
    }
}
