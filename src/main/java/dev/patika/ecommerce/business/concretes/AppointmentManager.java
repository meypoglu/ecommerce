package dev.patika.ecommerce.business.concretes;

import dev.patika.ecommerce.business.abstracts.IAppointmentService;
import dev.patika.ecommerce.core.exception.NotFoundException;
import dev.patika.ecommerce.core.utilities.Message;
import dev.patika.ecommerce.dao.AppointmentRepo;
import dev.patika.ecommerce.dao.AvailableDateRepo;
import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.AvailableDate;
import dev.patika.ecommerce.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateRepo availableDateRepo;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentManager.class);

    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public Appointment save(Appointment appointment) throws Exception {
        if (isDoctorAvailable(appointment.getDoctorAppointment().getId(), appointment.getAppointmentDate())) {
            Appointment savedAppointment = this.appointmentRepo.save(appointment);
            return savedAppointment;
        } else {
            throw new Exception("Doktor, belirtilen zamanda uygun değildir");
        }
    }

    @Override
    public Appointment get(Long id) {
        return appointmentRepo.findById(id.intValue()).orElseThrow(() -> new NotFoundException(Message.NOT_FOUND));
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
    public boolean delete(Long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    public List<Appointment> findByDoctorName(String doctorName) {
        return appointmentRepo.findByDoctorAppointmentName(doctorName);
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime) {
        if (dateTime.getMinute() != 0 || dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            return false;
        }

        List<AvailableDate> availableDateList = availableDateRepo.findByDoctorList_IdAndAvailableDate(doctorId, dateTime.toLocalDate());
        if (availableDateList.isEmpty()) {
            return false;
        }

        LocalDateTime startTime = dateTime;
        LocalDateTime endTime = dateTime.plusMinutes(59).plusSeconds(59);


        List<Appointment> appointments = appointmentRepo.findByDoctorAppointment_IdAndAppointmentDateBetween(doctorId, startTime, endTime);
        boolean isAvailable = appointments.isEmpty();

        if (!isAvailable) {
            for (Appointment app : appointments) {
            }
        }

        return isAvailable;
    }

    public List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return appointmentRepo.findByAnimal_IdAndAppointmentDateBetween(animalId, startDateTime, endDateTime);
    }

    public List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return appointmentRepo.findByDoctorAppointment_IdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
    }
}
