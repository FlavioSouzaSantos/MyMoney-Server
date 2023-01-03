package br.com.mymoney.authserver.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CheckPermissionDto (@NotBlank(message = "{validation.model.CheckPermissionDto.roleName.NotBlank}") String roleName,
                                  @NotBlank(message = "{validation.model.CheckPermissionDto.url.NotBlank}") String url,
                                  @NotBlank(message = "{validation.model.CheckPermissionDto.method.NotBlank}") String method) {
}
