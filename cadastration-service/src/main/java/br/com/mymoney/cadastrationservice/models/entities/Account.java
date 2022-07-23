package br.com.mymoney.cadastrationservice.models.entities;

import java.util.UUID;

public class Account {
    private Long id;
    private String name;
    private AccountType type;
    private Double initialValue;
    private boolean deleted;
    private UUID userId;
}
