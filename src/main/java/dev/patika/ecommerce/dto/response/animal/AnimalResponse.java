package dev.patika.ecommerce.dto.response.animal;

import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
import dev.patika.ecommerce.entities.Vaccine;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private Long customerId;
}
