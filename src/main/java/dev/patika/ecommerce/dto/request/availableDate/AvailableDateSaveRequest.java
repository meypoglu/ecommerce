package dev.patika.ecommerce.dto.request.availableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {

    @NotNull(message = "Tarih boş olamaz")
    private LocalDate date;

    @NotNull(message = "Doktor ID'si boş olamaz")
    private List<Long> doctorIds;
}
