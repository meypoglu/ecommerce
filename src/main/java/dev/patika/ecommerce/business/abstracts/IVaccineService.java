package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Doctor;
import dev.patika.ecommerce.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);
    Vaccine get(int id);
    Vaccine update(Vaccine vaccine);
    boolean delete(int id);
    Page<Vaccine> cursor(int page, int pageSize);
    List<Vaccine> findByDate(LocalDate startDate, LocalDate endDate);

}
