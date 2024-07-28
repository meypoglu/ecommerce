package dev.patika.ecommerce.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private long id;

    @NotNull(message = "Randevu tarihi boş olamaz")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Hayvan ID'si boş olamaz")
    private long animalId;

    @NotNull(message = "Doktor ID'si boş olamaz")
    private long doctorId;

    @NotNull(message = "Müsaitlik tarihi ID'si boş olamaz")
    private long availableDateId;
}
