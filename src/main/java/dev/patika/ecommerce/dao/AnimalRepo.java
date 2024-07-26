package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Integer> {
    List<Animal> findByNameContainingIgnoreCase(String name);

}
