package br.com.mymoney.authserver.models.dtos;

import java.util.UUID;

public record ResultCheckPermissionDto(UUID user, String url, String method, boolean permitted) {
}
