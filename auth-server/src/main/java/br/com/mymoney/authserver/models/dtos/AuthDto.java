package br.com.mymoney.authserver.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AuthDto(@NotBlank(message = "{validation.model.AuthDto.login.NotBlank}") String login,
                      @NotEmpty(message = "{validation.model.AuthDto.password.NotBlank}") String password) {
}
