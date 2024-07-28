package dev.patika.ecommerce.dto.response.appointment;

import dev.patika.ecommerce.entities.Animal;
import dev.patika.ecommerce.entities.Doctor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
    private Long availableDateId;
}
