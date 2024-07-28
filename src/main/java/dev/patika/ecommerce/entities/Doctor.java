package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class    Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    @NotBlank
    @Column(name = "doctor_name")
    private String name;

    @NotBlank
    @Column(name = "doctor_phone")
    private String phone;

    @NotBlank
    @Column(name = "doctor_mail")
    private String mail;

    @NotBlank
    @Column(name = "doctor_address")
    private String address;

    @NotBlank
    @Column(name = "doctor_city")
    private String city;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Animal> animals;

    @OneToMany(mappedBy = "doctorAppointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "doctorList", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AvailableDate> availableDates = new ArrayList<>();

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
