package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @NotBlank
    @Column(name = "vaccine_name")
    private String name;

    @NotBlank
    @Column(name = "vaccine_code")
    private String code;

    @NotNull
    @Column(name = "vaccine_start_date")
    private LocalDate protectionStartDate;

    @NotNull
    @Column(name = "vaccine_end_date")
    private LocalDate protectionEndDate;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}