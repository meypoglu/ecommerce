package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(int id);
    Doctor update(Doctor doctor);
    boolean delete(int id);
    Page<Doctor> cursor(int page, int pageSize);
    List<Doctor> findByName(String name);
}
