package br.com.mymoney.cadastrationservice.models.entities;

import java.util.UUID;

public class CreditCar {
    private Long id;
    private String name;
    private CreditCarType type;
    private Account account;
    private int closingDay;
    private int payingDay;
    private Double limit;
    private boolean deleted;
    private boolean active;
    private UUID userId;
}
