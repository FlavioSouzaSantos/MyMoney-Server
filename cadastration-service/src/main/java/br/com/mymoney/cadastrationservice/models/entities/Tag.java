package br.com.mymoney.cadastrationservice.models.entities;

import java.util.UUID;

public class Tag {
    private Long id;
    private String name;
    private boolean deleted;
    private boolean active;
    private UUID userId;
}
