package br.com.mymoney.cadastrationservice.models.entities;

import java.util.UUID;

public class Category {
    private Long id;
    private String name;
    private Category parent;
    private boolean deleted;
    private boolean active;
    private UUID userId;
}
