package dev.patika.ecommerce.dto.request.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSaveRequest {
    private String name;
    private String birthDate;
    private String country;
}
