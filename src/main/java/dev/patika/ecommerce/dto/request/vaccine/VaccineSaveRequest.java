package dev.patika.ecommerce.dto.request.vaccine;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {

    @NotBlank(message = "Aşı ismi boş olamaz")
    private String name;

    @NotBlank(message = "Aşı kodu boş olamaz")
    private String code;

    @NotBlank(message = "Aşı koruyuculuk başlangıç tarihi boş olamaz")
    private LocalDate startDate;

    @NotBlank(message = "Aşı koruyuculuk sonlanma tarihi boş olamaz")
    private LocalDate endDate;
}
