package com.example.serviceproduits.controller;

import com.example.serviceproduits.model.Produits;
import com.example.serviceproduits.repository.ProduitRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commadeapp")
public class ProduitsRestController implements HealthIndicator {

    private ProduitRepository produitRepository;
    public ProduitsRestController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }


    @GetMapping("/produits")
    public List<Produits> produitsList(){

        return produitRepository.findAll();
    }



    @GetMapping("/produits/{id}")
    public Produits commandesById(@PathVariable Long id){

        return produitRepository.findById(id).get();
    }



    @PostMapping("/produits")
    public ResponseEntity<Produits> addCommande(@RequestBody Produits produits){
        produitRepository.save(produits);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }



    @DeleteMapping("produits/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id){
        if (!produitRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        produitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PutMapping("/produits/{id}")
    public ResponseEntity<Produits> updateProduit(@PathVariable Long id, @RequestBody Produits updatedProduit) {
        Optional<Produits> existingProduitOptional = produitRepository.findById(id);

        if (existingProduitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Produits existingProduit = existingProduitOptional.get();
        existingProduit.setName(updatedProduit.getName());
        existingProduit.setDescription(updatedProduit.getDescription());
        produitRepository.save(existingProduit);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Produits> products = produitRepository.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}