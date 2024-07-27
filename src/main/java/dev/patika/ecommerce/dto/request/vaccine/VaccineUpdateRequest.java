package dev.patika.ecommerce.dto.request.vaccine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {

    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private long id;

    @NotBlank(message = "Aşı ismi boş olamaz")
    private String name;

    @NotBlank(message = "Aşı kodu boş olamaz")
    private String code;

    @NotNull(message = "Aşı koruyuculuk başlangıç tarihi boş olamaz")
    private LocalDate protectionStartDate;

    @NotNull(message = "Aşı koruyuculuk sonlanma tarihi boş olamaz")
    private LocalDate protectionEndDate;
}
