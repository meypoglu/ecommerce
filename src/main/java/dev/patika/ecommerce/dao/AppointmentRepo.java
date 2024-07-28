package dev.patika.ecommerce.dao;

import dev.patika.ecommerce.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctorAppointment_IdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);
    List<Appointment> findByDoctorAppointmentName(String doctorName);

}
