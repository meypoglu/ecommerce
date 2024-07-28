package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @NotNull
    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @ManyToOne
    @JoinColumn(name = "appointment_animal_id", referencedColumnName = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name ="appointment_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctorAppointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_available_date_id", referencedColumnName = "available_date_id")
    private AvailableDate availableDate;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", animal=" + (animal != null ? animal.getId() : "null") +
                ", doctorAppointment=" + (doctorAppointment != null ? doctorAppointment.getId() : "null") +
                ", availableDate=" + (availableDate != null ? availableDate.getId() : "null") +
                '}';
    }
}
