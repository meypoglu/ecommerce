package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Integer> {
    List<Vaccine> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
}
