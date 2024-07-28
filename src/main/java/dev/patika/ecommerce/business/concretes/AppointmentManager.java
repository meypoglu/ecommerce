package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AppointmentRepo;
import dev.patika.ecommerce.dao.AvailableDateRepo;
import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateRepo availableDateRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public Appointment save(Appointment appointment) throws Exception {
        if (isDoctorAvailable(appointment.getDoctorAppointment().getId(), appointment.getAppointmentDate())) {
            return this.appointmentRepo.save(appointment);
        } else {
            throw new Exception("Doktor, belirtilen zamanda uygun değildir");
        }
    }

    @Override
    public Appointment get(long id) {
        return appointmentRepo.findById((int) id).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
    }

    @Override
    public Appointment update(Appointment appointment) throws Exception {
        if (isDoctorAvailable(appointment.getDoctorAppointment().getId(), appointment.getAppointmentDate())) {
            this.get(appointment.getId());
            return appointmentRepo.save(appointment);
        } else {
            throw new Exception("Doktor, belirtilen zamanda uygun değildir");
        }
    }

    @Override
    public boolean delete(long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime) {
        List<AvailableDate> availableDateList = availableDateRepo.findByDoctors_IdAndAvailableDate(doctorId, dateTime.toLocalDate());
        if (availableDateList.isEmpty()) {
            return false;
        }
        List<Appointment> appointments = appointmentRepo.findByDoctorAppointment_IdAndAppointmentDate(doctorId, dateTime);
        return appointments.isEmpty();
    }

    public List<Appointment> findByDoctorName(String doctorName) {
        return appointmentRepo.findByDoctorAppointmentName(doctorName);
    }
}
