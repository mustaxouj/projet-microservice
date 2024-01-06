package com.example.servicecommandes.model;

import com.example.servicecommandes.model.Produits;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class Commandes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeId;
    private String description;
    private Integer quantite;
    private LocalDate dateDeCreation;
    private Double montant;

    @Transient //cela signifié ignorer ce attribut
    private Produits produits;
    private Long produitId;

}
