package dev.patika.ecommerce.dto.request.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {
    @NotBlank(message = "Müşteri ismi boş olamaz")
    private String name;

    @NotBlank(message = "Telefon numarası boş olamaz")
    private String phone;

    @NotBlank(message = "Mail adresi boş olamaz")
    private String mail;

    @NotBlank(message = "Adres boş olamaz")
    private String address;

    @NotBlank(message = "Şehir ismi boş olamaz")
    private String city;

}
