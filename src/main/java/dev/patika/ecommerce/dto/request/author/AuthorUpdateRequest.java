package dev.patika.ecommerce.dto.request.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorUpdateRequest {
    private int id;
    private String name;
    private String birthDate;
    private String country;
}
