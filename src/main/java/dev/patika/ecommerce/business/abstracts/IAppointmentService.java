package dev.patika.ecommerce.business.abstracts;

import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment) throws Exception;
    Appointment get(long id);
    Appointment update(Appointment appointment) throws Exception;
    boolean delete(long id);
    Page<Appointment> cursor(int page, int pageSize);
    List<Appointment> findByDoctorName(String doctorName);
}
