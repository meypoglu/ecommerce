package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByProtectionEndDateBetween(LocalDate protectionStartDate, LocalDate protectionEndDate);
    List<Vaccine> findByNameAndCodeAndAnimal_Id(String name, String code, Long animalId);
    List<Vaccine> findByAnimalId(Long animalId);


}