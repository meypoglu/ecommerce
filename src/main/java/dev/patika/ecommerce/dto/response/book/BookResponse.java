package dev.patika.ecommerce.dto.response.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private int id;
    private String name;
    private String publicationYear;
    private int stock;
    private int authorId;
    private int publisherId;
}
