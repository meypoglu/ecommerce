package dev.patika.ecommerce.dto.request.appointment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {
    @NotNull(message = "Randevu tarihi boş olamaz")
    private LocalDateTime appointmentDate;

    @Positive(message = "Hayvan ID'si boş olamaz")
    private Long animalId;

    @Positive(message = "Doktor ID'si boş olamaz")
    private Long doctorId;

    @Positive(message = "Müsaitlik tarihi ID'si boş olamaz")
    private Long availableDateId;
}
