package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Integer> {
    List<AvailableDate> findByDoctors_IdAndAvailableDate(Long doctorId, LocalDate date);
    List<AvailableDate> findByDoctors_Id(Long doctorId);

}
