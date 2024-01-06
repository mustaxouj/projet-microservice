package com.example.servicecommandes;

import com.example.servicecommandes.service.ProduitsRestClient;
import com.example.servicecommandes.model.Commandes;
import com.example.servicecommandes.repository.CommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableFeignClients
public class ServiceCommandesApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceCommandesApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CommandeRepository commandeRepository , ProduitsRestClient produitsRestClient){
        return  args -> {

            produitsRestClient.allProduits().forEach(p->{

                Commandes commandes1 = Commandes.builder()
                        .description("La commande Numero: "+ String.valueOf(p.getId()))
                        .montant(Math.random()*100)
                        .quantite(1)
                        .dateDeCreation(LocalDate.now())
                        .produitId(p.getId())
                        .build();

                commandeRepository.save(commandes1);

            });
        };
    }

}
