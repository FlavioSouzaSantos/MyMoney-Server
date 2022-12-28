package br.com.mymoney.crudcommon.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {
    private String field;
    private String error;

    public ResponseErrorDto(FieldError fieldError){
        field = fieldError.getField();
        error = fieldError.getDefaultMessage();
    }

    public ResponseErrorDto(String error) {
        this.error = error;
    }
}
