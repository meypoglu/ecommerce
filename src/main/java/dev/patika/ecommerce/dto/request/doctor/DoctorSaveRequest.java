package dev.patika.ecommerce.dto.request.doctor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
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
