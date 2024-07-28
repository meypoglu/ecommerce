package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "available_dates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private long id;

    @NotNull
    @Column(name = "available_date")
    private LocalDate availableDate;

    @ManyToMany(mappedBy = "availableDates")
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "availableDate")
    private List<Appointment> appointments = new ArrayList<>();
}
