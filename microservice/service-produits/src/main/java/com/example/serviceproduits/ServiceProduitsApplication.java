package com.example.serviceproduits;

import com.example.serviceproduits.model.Produits;
import com.example.serviceproduits.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ServiceProduitsApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceProduitsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProduitRepository produitRepository){
        return args -> {
            List<Produits> produitsList = List.of(
                     Produits.builder()
                            .name("Produit electronique")
                            .description("c'est un electronique")
                            .build(),

                    Produits.builder()
                            .name("Produit cosmetique")
                            .description("c'est un cosmetique")
                            .build(),

                    Produits.builder()
                            .name("Produit logiciel")
                            .description("c'est un logiciel")
                            .build(),

                    Produits.builder()
                            .name("Produit alimentaire")
                            .description("c'est un alimentaire")
                            .build(),

                    Produits.builder()
                            .name("Produit jardinage")
                            .description("c'est un jardinage")
                            .build()
            );

            produitRepository.saveAll(produitsList);
        };
    }

}
