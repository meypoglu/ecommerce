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
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private long id;

    @NotBlank
    @Column(name = "vaccine_name")
    private String name;

    @NotBlank
    @Column(name = "vaccine_code")
    private String code;

    @NotNull
    @Column(name = "vaccine_strt_date")
    private LocalDate protectionStartDate;

    @NotNull
    @Column(name = "vaccine_end_date")
    private LocalDate protectionEndDate;

    @ManyToMany(mappedBy = "vaccineList")
    private List<Animal> animalList = new ArrayList<>();
}
