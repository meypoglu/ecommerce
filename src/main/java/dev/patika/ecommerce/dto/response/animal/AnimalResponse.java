package dev.patika.ecommerce.dto.response.animal;

import dev.patika.ecommerce.entities.Appointment;
import dev.patika.ecommerce.entities.Customer;
import dev.patika.ecommerce.entities.Doctor;
import dev.patika.ecommerce.entities.Vaccine;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalResponse {
    private int id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
}
