package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByNameContainingIgnoreCase(String name);

}
