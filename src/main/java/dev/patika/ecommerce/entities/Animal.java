package dev.patika.ecommerce.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"animal_name", "animal_species", "animal_breed", "animal_gender", "animal_colour", "animal_birth_date"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_sequence")
    @SequenceGenerator(name = "animal_sequence", sequenceName = "animal_seq", allocationSize = 1)
    @Column(name = "animal_id")
    private Long id;

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

    @NotNull
    @Column(name = "animal_birth_date")
    private LocalDate dateOfBirth;

    @ManyToOne()
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "animal_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccine> vaccineList = new ArrayList<>();
}