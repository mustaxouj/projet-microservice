package com.example.servicecommandes.Controller;

import com.example.servicecommandes.service.ProduitsRestClient;
import com.example.servicecommandes.configuration.ApplicationPropertiesConfiguration;
import com.example.servicecommandes.model.Commandes;
import com.example.servicecommandes.model.Produits;
import com.example.servicecommandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commadeapp")
public class CommanndesRestController implements HealthIndicator {

    private CommandeRepository commandeRepository;
    private ProduitsRestClient produitsRestClient;

    @Autowired
    ApplicationPropertiesConfiguration applicationPropertiesConfiguration;

    public CommanndesRestController(CommandeRepository commandeRepository, ProduitsRestClient produitsRestClient) {
        this.commandeRepository = commandeRepository;
        this.produitsRestClient = produitsRestClient;
    }




    @GetMapping("/commandes")
    public List<Commandes> commandesList(){

        List<Commandes> commandes = commandeRepository.findAll();
        commandes.forEach(c->{
            c.setProduits(produitsRestClient.findProduitsById(c.getProduitId()));
        });

        List<Commandes> listeLimite = commandes.subList(0,applicationPropertiesConfiguration.getLimitDeCommands());
        System.out.println(listeLimite);
        return listeLimite;
    }


    @GetMapping("/commandes/{id}")
    public Commandes commandesById(@PathVariable Long id){

        Commandes commande =  commandeRepository.findById(id).get();
        Produits produit = produitsRestClient.findProduitsById(commande.getProduitId());
        System.out.println(produit.toString());
        commande.setProduits(produit);
        return commande;
    }


    @PostMapping("/commandes")
    public ResponseEntity<Commandes> addCommande(@RequestBody Commandes commande) {
        Produits produit = produitsRestClient.findProduitsById(commande.getProduitId());

        if (produit != null) {
            commande.setProduits(produit);
            Commandes savedCommande = commandeRepository.save(commande);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCommande);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




    @PutMapping("/commandes/{id}")
    public ResponseEntity<Commandes> updateCommande(@PathVariable Long id, @RequestBody Commandes updatedCommande) {
        Commandes existingCommande = commandeRepository.findById(id).orElse(null);
        if (existingCommande != null) {
            Produits produit = produitsRestClient.findProduitsById(updatedCommande.getProduitId());
            if (produit != null) {
                existingCommande.setDateDeCreation(updatedCommande.getDateDeCreation());
                existingCommande.setDescription(updatedCommande.getDescription());
                existingCommande.setMontant(updatedCommande.getMontant());
                existingCommande.setQuantite(updatedCommande.getQuantite());
                existingCommande.setProduits(produit);
                commandeRepository.save(existingCommande);
                return ResponseEntity.ok(existingCommande);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable Long id) {
        if (commandeRepository.existsById(id)) {
            commandeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Commandes> products = commandeRepository.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
