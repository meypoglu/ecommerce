package dev.patika.ecommerce.dto.request.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Species is required")
    private String species;

    @NotBlank(message = "Breed is required")
    private String breed;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Positive(message = "Customer ID is required")
    private Long customerId;

    @Positive(message = "Doctor ID must be positive")
    private Long doctorId;
}