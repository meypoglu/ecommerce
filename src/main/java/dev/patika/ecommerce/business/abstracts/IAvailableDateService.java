package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(Long id);
    AvailableDate update(AvailableDate availableDate);
    boolean delete(Long id);
    Page<AvailableDate> cursor(int page, int pageSize);
    AvailableDate getAvailableDateById(Long id);
    List<AvailableDate> findByDoctorIdsAndDate(Long doctorId, LocalDate date);
    List<AvailableDate> findByDoctorIds(Long doctorId);
    List<AvailableDate> getAllAvailableDates();
}
