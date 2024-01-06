package com.example.servicecommandes.repository;

import com.example.servicecommandes.model.Commandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommandeRepository extends JpaRepository<Commandes,Long> {
}
