package dev.patika.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;

    @NotBlank
    @Column(name = "customer_name")
    private String name;

    @NotBlank
    @Column(name = "customer_phone")
    private String phone;

    @NotBlank
    @Column(name = "customer_mail")
    private String mail;

    @NotBlank
    @Column(name = "customer_address")
    private String address;

    @NotBlank
    @Column(name = "customer_city")
    private String city;

    @OneToMany(mappedBy = "customer")
    private List<Animal> animals;
}
