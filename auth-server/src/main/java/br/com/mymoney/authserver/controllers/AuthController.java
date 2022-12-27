package br.com.mymoney.authserver.controllers;

import br.com.mymoney.authserver.models.dtos.AuthDto;
import br.com.mymoney.authserver.models.dtos.TokenDto;
import br.com.mymoney.authserver.services.AuthService;
import br.com.mymoney.crudcommon.exceptions.ResponseErrorException;
import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenDto> auth(@Valid @RequestBody AuthDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Set<ResponseErrorDto> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseErrorDto::new)
                    .collect(Collectors.toSet());

            throw new ResponseErrorException(errors);
        }
        return ResponseEntity.ok(authService.auth(dto));
    }
}
