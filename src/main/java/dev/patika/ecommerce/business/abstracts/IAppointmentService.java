package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment) throws Exception;

    Appointment get(Long id);

    Appointment update(Appointment appointment) throws Exception;

    boolean delete(Long id);

    Page<Appointment> cursor(int page, int pageSize);

    List<Appointment> findByDoctorName(String doctorName);

    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
