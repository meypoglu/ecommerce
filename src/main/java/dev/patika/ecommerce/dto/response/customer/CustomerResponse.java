package dev.patika.ecommerce.dto.response.customer;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private int id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
