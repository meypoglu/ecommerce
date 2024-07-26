package dev.patika.ecommerce.dto.request.animal;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

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

    @NotBlank(message = "Hayvan doğum tarihi boş olamaz")
    private LocalDate dateOfBirth;
}
