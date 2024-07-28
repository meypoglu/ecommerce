package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findByNameContainingIgnoreCase(String name);

    List<Animal> findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirth(
            String name, String species, String breed, String gender, String colour, LocalDate dateOfBirth
    );
    List<Animal> findByCustomerId(Long customerId);

}
