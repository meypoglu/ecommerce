package dev.patika.ecommerce.dto.request.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    @NotBlank(message = "Hayvan adı boş olamaz")
    private String name;

    @NotBlank(message = "Hayvan türü boş olamaz")
    private String species;

    @NotBlank(message = "Hayvan cinsi boş olamaz")
    private String breed;

    @NotBlank(message = "Hayvan cinsiyeti boş olamaz")
    private String gender;

    @NotBlank(message = "Hayvan rengi boş olamaz")
    private String colour;

    @NotNull(message = "Hayvan doğum tarihi boş olamaz")
    private LocalDate dateOfBirth;

    @Positive(message = "Hayvan sahip ID'si boş olamaz")
    private int customerId;

    @Positive(message = "Hayvan doktor ID'si boş olamaz")
    private int doctorId;

    @NotEmpty(message = "Hayvan randevu ID'si boş olamaz")
    private List<Long> appointmentIds;

    @NotEmpty(message = "Hayvan aşı ID'si boş olamaz")
    private List<Long> vaccineIds;
}
