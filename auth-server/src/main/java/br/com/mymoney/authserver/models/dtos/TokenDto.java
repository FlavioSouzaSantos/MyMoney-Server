package br.com.mymoney.authserver.models.dtos;

import java.time.ZonedDateTime;

public record TokenDto(String token, ZonedDateTime dateTimeExpiration) {
}
