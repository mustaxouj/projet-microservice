package com.example.servicecommandes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Produits {

    private Long id;
    private String name;
    private String description;
}