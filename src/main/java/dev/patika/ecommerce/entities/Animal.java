package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private long id;

    @NotBlank
    @Column(name = "animal_name")
    private String name;

    @NotBlank
    @Column(name = "animal_species")
    private String species;

    @NotBlank
    @Column(name = "animal_breed")
    private String breed;

    @NotBlank
    @Column(name = "animal_gender")
    private String gender;

    @NotBlank
    @Column(name = "animal_colour")
    private String colour;

    @NotBlank
    @Column(name = "animal_birth_date")
    private LocalDate dateOfBirth;

    @ManyToOne()
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "animal_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(name = "animalToVaccine", joinColumns = {@JoinColumn(name = "animal_id")}, inverseJoinColumns = {@JoinColumn(name = "vaccine_id")})
    private List<Vaccine> vaccineList = new ArrayList<>();
}
