package dev.patika.ecommerce.dto.response.appointment;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Doctor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentResponse {
    private long id;
    private LocalDateTime appointmentDate;
    private long animalId;
    private long doctorId;
    private long availableDateId;
}
